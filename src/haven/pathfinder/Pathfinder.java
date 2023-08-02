package haven.pathfinder;


import haven.*;
import haven.render.Model;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static haven.OCache.posres;

public class Pathfinder implements Runnable {
    private OCache oc;
    private MCache map;
    private MapView mv;
    private Coord dest;
    public boolean terminate = false;
    public boolean moveinterupted = false;
    private int meshid;
    private int clickb;
    private Gob gob;
    private String action;
    public Coord mc;
    private int modflags;
    private int interruptedRetries = 5;
    private static final int RESPONSE_TIMEOUT = 800;

    public Pathfinder(MapView mv, Coord dest, String action) {
        this.dest = dest;
        this.action = action;
        this.oc = mv.glob.oc;
        this.map = mv.glob.map;
        this.mv = mv;
    }

    public Pathfinder(MapView mv, Coord dest, Gob gob, int meshid, int clickb, int modflags, String action) {
        this.dest = dest;
        this.meshid = meshid;
        this.clickb = clickb;
        this.gob = gob;
        this.modflags = modflags;
        this.action = action;
        this.oc = mv.glob.oc;
        this.map = mv.glob.map;
        this.mv = mv;
    }

    private final Set<PFListener> listeners = new CopyOnWriteArraySet<PFListener>();
    public final void addListener(final PFListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(final PFListener listener) {
        listeners.remove(listener);
    }

    private final void notifyListeners() {
        for (PFListener listener : listeners) {
            listener.pfDone(this);
        }
    }

    @Override
    public void run() {
        do {
            moveinterupted = false;
            pathfind(mv.player().rc.floor());
        } while (moveinterupted && !terminate);
        notifyListeners();
    }

    public void pathfind(Coord src) {
        long starttotal = System.nanoTime();
        Map m = new Map(src, dest, map);
        Gob player = mv.player();

        long start = System.nanoTime();
        synchronized (oc) {
            for (Gob gob : oc) {
                if (gob.isplayer(this.mv.gameui())){
                    continue;
                }
                // need to exclude destination gob so it won't get into TO candidates list
                if (this.gob != null && this.gob.id == gob.id) {
                    continue;
                }
                if (gob.collisionBox == null || gob.collisionBox.fx == null || gob.collisionBox.fx.model == null) {
                    continue;
                }
                Model.BoundingBox box = gob.collisionBox.fx.model.bbox;
                if (box != null && isInsideBoundBox(gob.rc.floor(), gob.a, box, player.rc.floor())) {
                    m.excludeGob(gob);
                    continue;
                }
                m.analyzeGobHitBoxes(gob);
            }
        }

        // if player is located at a position occupied by a gob (can happen when starting too close to gobs)
        // move it slightly away from it
        if (m.isOriginBlocked()) {
            Pair<Integer, Integer> freeloc = m.getFreeLocation();

            if (freeloc == null) {
                terminate = true;
                m.dbgdump();
                return;
            }

            mc = new Coord2d(src.x + freeloc.a - Map.origin, src.y + freeloc.b - Map.origin).floor(posres);
            mv.wdgmsg("click", Coord.z, mc, 1, 0);

            // FIXME
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // need to recalculate map
            moveinterupted = true;
            m.dbgdump();
            return;
        }

        // exclude any bounding boxes overlapping the destination gob
        if (this.gob != null)
            m.excludeGob(this.gob);

        if (Map.DEBUG_TIMINGS)
            System.out.println("      Gobs Processing: " + (double) (System.nanoTime() - start) / 1000000.0 + " ms.");

        Iterable<Edge> path = m.main();
        if (Map.DEBUG_TIMINGS)
            System.out.println("--------------- Total: " + (double) (System.nanoTime() - starttotal) / 1000000.0 + " ms.");

        m.dbgdump();
        //System.out.println("path length: " + path.spliterator().getExactSizeIfKnown());
        Iterator<Edge> it = path.iterator();
        while (it.hasNext() && !moveinterupted && !terminate) {
            Edge e = it.next();

            mc = new Coord2d(src.x + e.dest.x - Map.origin, src.y + e.dest.y - Map.origin).floor(posres);
            //System.out.println("moving to mc: " + mc);

            if (action != null && !it.hasNext())
                mv.gameui().act(action);

            if (gob != null && !it.hasNext())
                mv.wdgmsg("click", Coord.z, mc, clickb, modflags, 0, (int) gob.id, gob.rc.floor(posres), 0, meshid);
            else
                mv.wdgmsg("click", Coord.z, mc, 1, 0);

            // wait for gob to start moving
            long moveWaitStart = System.currentTimeMillis();
            while (!player.isMoving() && !terminate) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    return;
                }
                if (System.currentTimeMillis() - moveWaitStart > RESPONSE_TIMEOUT)
                    return;
            }

            // wait for it to finish
            while (!moveinterupted && !terminate) {
                if (!player.isMoving()) {
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e1) {
                        return;
                    }
                    if (!player.isMoving())
                        break;
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    return;
                }

                long now = System.currentTimeMillis();

                // FIXME
                // when right clicking gobs, char will try to navigate towards gob's rc
                // however he will be blocked by gob's bounding box.
                // therefore we just wait for a bit
                LinMove lm = player.getLinMove();
                if (gob != null && !it.hasNext() && lm != null && now - lm.lastupd > 500)
                    break;
            }

            if (moveinterupted) {
                interruptedRetries--;
                if (interruptedRetries == 0)
                    terminate = true;
                m.dbgdump();
                return;
            }
        }
        terminate = true;
        OptWnd.toggleGobCollisionBoxesDisplayCheckBox.set(false);
    }

    static public boolean isInsideBoundBox(Coord gobRc, double gobA, Model.BoundingBox gobBBox, Coord point) {
        final Coordf relative = new Coordf(point.sub(gobRc)).rotate(-gobA);
        return relative.x >= gobBBox.a.x && relative.x <= gobBBox.b.x &&
               relative.y >= gobBBox.a.y && relative.y <= gobBBox.b.y;
    }
}

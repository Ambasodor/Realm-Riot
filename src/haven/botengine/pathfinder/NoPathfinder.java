package haven.botengine.pathfinder;

import haven.*;
import haven.botengine.BotEnvironment;
import haven.botengine.BotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static haven.OCache.posres;

public class NoPathfinder implements Pathfinder {

    private static final Logger log = LoggerFactory.getLogger(NoPathfinder.class);
    private static final int MOVEMENT_ACCURACY = 8;

    private final BotEnvironment environment;

    public NoPathfinder(BotEnvironment environment) {
        this.environment = environment;
    }


    @Override
    public void move(float x, float y) {
        Coord2d playerCoordinate = environment.getGui().map.player().rc;
        moveTo((float) playerCoordinate.x + x, (float) playerCoordinate.y + y);
    }

    @Override
    public void moveTo(Coord2d coordinate) {
        moveTo((float) coordinate.x, (float) coordinate.y);
    }

    @Override
    public void moveTo(Gob gob) {
        moveTo((float) gob.rc.x, (float) gob.rc.y);
    }
    @Override
    public void moveToMussel(Gob gob){
        moveToMussel((int) gob.rc.x, (int) gob.rc.y);
    }
    @Override
    public void moveToMussel(Coord2d coordinate) {
        moveToMussel((float) coordinate.x, (float) coordinate.y);
    }
    @Override
    public void moveToClay(Coord2d coordinate) {
        moveToClay((float) coordinate.x, (float) coordinate.y);
    }
    @Override
    public void moveToClay(Gob gob){
        moveToClay((int) gob.rc.x, (int) gob.rc.y);
    }

    @Override
        public void moveTo(float x, float y) {
        Coord2d destination = new Coord2d(x, y);
        double distanceLeft;
        int multiplier = 24;
        distanceLeft = environment.getGui().map.player().rc.dist(destination);
        do {
            moveToNoPF(destination);
            beforerun(destination);
            waitUntilStops();
            distanceLeft = environment.getGui().map.player().rc.dist(destination);
            if (distanceLeft != 0) {
                if (multiplier > 400) throw new RuntimeException("Could not move to the given destination");
                log.debug("Trying to find another route, distance left {}", distanceLeft);
                moveWithoutUnblocking(
                        (int) Math.round(Math.random() * multiplier - multiplier / 2.0),
                        (int) Math.round(Math.random() * multiplier - multiplier / 2.0)
                );
                multiplier += 4;
                beforerun(destination);
                waitUntilStops();
                distanceLeft = environment.getGui().map.player().rc.dist(destination);
            }
        } while (distanceLeft != 0.0);
    }
    public List<Gob> findObjects(String resourceName) {
        synchronized (environment.getGui().map.getGlob().oc) {
            Gob player = environment.getGui().map.player();

            if (player != null) {
                Coord2d playerCoord = player.rc;
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, resourceName))
                        .sorted(Comparator.comparing(gob -> gob.rc.dist(playerCoord)))
                        .collect(Collectors.toList());
            } else {
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, resourceName))
                        .collect(Collectors.toList());
            }
        }
    }
    @Override
    public void moveToMussel(float x, float y) {
        Coord2d destination = new Coord2d(x, y);
        double distanceLeft;
        int multiplier = 24;
        int beforebreak = 0;
        int beforemessage = 0;
        boolean aggro = isAggroed();
        if (findObjects("rowboat") == null){
            while (findObjects("rowboat") == null){
                List<Gob> check = findObjects("rowboat");
                if (!check.isEmpty()) break;
                else {
                    if (beforemessage == 50){
                        environment.getDiscord().sendMessage("I HAVE NO BOAT, SOMEONE DESTROYED IT");
                        throw new RuntimeException("No rowboat");
                    }
                    beforemessage++;
                }
            }
        }
        if (findObjects("rowboat") != null) {
            Gob gob = findObjects("rowboat").get(0);
            if (!aggro) {
                do {
                    aggro = isAggroed();
                    if (aggro) break;
                    moveToNoPF(destination);
                    beforeswim(destination);
                    MusselwaitUntilStops();
                    distanceLeft = gob.rc.dist(destination);
                    if (distanceLeft > 0.1) {
                        aggro = isAggroed();
                        if (aggro) break;
                        if (beforebreak >= 2) break;
                        if (multiplier > 400) throw new RuntimeException("Could not move to the given destination");
                        beforebreak++;
                        log.debug("Trying to find another route, distance left {}", distanceLeft);
                        moveWithoutUnblocking(
                                (int) Math.round(Math.random() * multiplier - multiplier / 2.0),
                                (int) Math.round(Math.random() * multiplier - multiplier / 2.0)
                        );
                        beforebreak++;
                        multiplier += 4;
                        beforeswim(destination);
                        MusselwaitUntilStops();
                        distanceLeft = gob.rc.dist(destination);
                    }
                } while (distanceLeft > 0.1);
            }
        }
    }
    @Override
    public void moveToClay(float x, float y) {
        Coord2d destination = new Coord2d(x, y);
        double distanceLeft;
        int multiplier = 24;
        distanceLeft = environment.getGui().map.player().rc.dist(destination);
        do {
            moveToNoPF(destination);
            beforerunclay();
            ClaywaitUntilStops();
            distanceLeft = environment.getGui().map.player().rc.dist(destination);
            if (distanceLeft != 0) {
                if (multiplier > 400) throw new RuntimeException("Could not move to the given destination");
                log.debug("Trying to find another route, distance left {}", distanceLeft);
                moveWithoutUnblocking(
                        (int) Math.round(Math.random() * multiplier - multiplier / 2.0),
                        (int) Math.round(Math.random() * multiplier - multiplier / 2.0)
                );
                multiplier += 4;
                beforerunclay();
                ClaywaitUntilStops();
                distanceLeft = environment.getGui().map.player().rc.dist(destination);
            }
        } while (distanceLeft != 0.0);
    }
    public void LeftClickCoord(Coord2d coordinate){
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 0);
    }
    public void moveToNoPF(Coord2d coordinate) {
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 0);
    }
    public Boolean isAggroed(){
        if (environment.getGui().fv.current != null){
            return true;
        }
        if (environment.getGui().fv.current == null){
            return false;
        }
        return false;
    }
    @Override
    public void sidestep(double distance) {
        double x = Math.random() * distance - distance / 2;
        double y = Math.random() * distance - distance / 2;

        moveWithoutUnblocking((int) x, (int) y);
        waitUntilStops();
    }

    // --------------------------------------------------
    // Movement
    // --------------------------------------------------

    private void moveWithoutUnblocking(int x, int y) {
        Coord2d playerCoord = environment.getGui().map.player().rc;
        environment.getGui().map.wdgmsg("click", Coord.z, new Coord2d(playerCoord.x + x, playerCoord.y + y).floor(posres), 1, 0);
    }

    // --------------------------------------------------
    // Helper methods
    // --------------------------------------------------
    public void beforerunclay(){
        boolean moving;
        double distanceLeft;
        int i = 0;
        Gob gob;
        gob = environment.getGui().map.player();
        if (gob != null) {
            moving = getGobPoses(gob, "idle");
            do {
                gob = environment.getGui().map.player();
                moving = getGobPoses(gob, "idle");
                if (i == 5) break;
                if (!moving) break;
                try{
                    Thread.sleep(200);
                } catch (Exception e) {
                }
                i++;
            } while (moving);
            log.trace("Character start running.");
        }
    }
    public void ClaywaitUntilStops() {
        log.trace("Waiting until the character stops.");
        boolean walking;
        Gob gob;
        boolean aggro = isAggroed();
        gob = getMyself();
        if (!aggro) {
            if (gob != null) {
                do {
                    aggro = isAggroed();
                    if (aggro) break;
                    gob = getMyself();
                    walking = getGobPoses(gob, "walking");
                    if (!walking) break;
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                } while (walking);
                log.trace("Character stopped.");
            }
        }
    }
public void beforerun(Coord2d destination){
    boolean moving;
    double distanceLeft;
    int i = 0;
    Gob gob;
    gob = environment.getGui().map.player();
    if (gob != null) {
        moving = getGobPoses(gob, "idle");
        do {
            gob = environment.getGui().map.player();
            moving = getGobPoses(gob, "idle");
            distanceLeft = environment.getGui().map.player().rc.dist(destination);
            if (distanceLeft == 0.0)break;
            if (i == 5) break;
            try{
                Thread.sleep(200);
            } catch (Exception e) {
            }
            i++;
        } while (moving);
        log.trace("Character start running.");
    }
}

    public void beforeswim(Coord2d destination){
        boolean moving;
        double distanceLeft;
        int i = 0;
        Gob gob;
        gob = environment.getGui().map.player();
        boolean aggro = isAggroed();
        if (!aggro) {
            if (gob != null) {
                moving = getGobPoses(gob, "rowboat-d");
                do {
                    aggro = isAggroed();
                    if (aggro) break;
                    gob = environment.getGui().map.player();
                    moving = getGobPoses(gob, "rowboat-d");
                    distanceLeft = environment.getGui().map.player().rc.dist(destination);
                    if (distanceLeft == 0.0) break;
                    if (i == 5) break;
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                    }
                    i++;
                } while (moving);
                log.trace("Character start running.");
            }
        }
    }
    public void waitUntilStops() {
        log.trace("Waiting until the character stops.");
        boolean moving;
        Gob gob;
        gob = environment.getGui().map.player();
        if (gob != null) {
            moving = getGobPoses(gob, "running");
            while (moving){
                gob = environment.getGui().map.player();
                moving = getGobPoses(gob, "running");
                if (!moving)break;
            }
            log.trace("Character stopped.");
        }
    }
    public boolean getGobPoses(Gob gob, String string) {
        if (gob != null) {
            Drawable d = gob.getattr(Drawable.class);

            if (d instanceof Composite) {
                Composite comp = (Composite) d;
                if (!comp.oldposes.isEmpty() || comp.oldposes == null) {
                    while (!comp.oldposes.isEmpty() || comp.oldposes == null) {
                        Collection<ResData> old = comp.oldposes;
                        if (comp.oldposes.isEmpty() || comp.oldposes != null) break;
                    }
                }
                if (comp.oldposes.isEmpty() || comp.oldposes != null) {
                    for (ResData rd : comp.oldposes) {
                        try {
                            if (rd.res.get().name.contains(string)) {
                                return true;
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return false;
    }
    public double distanceTo(Gob gob) {
        return environment.getGui().map.player().rc.dist(gob.rc);
    }
    public Gob getMyself(){
        return environment.getGui().map.player();
    }
    public void MusselwaitUntilStops() {
        log.trace("Waiting until the character stops.");
        boolean moving;
        Gob gob;
        boolean aggro = isAggroed();
        gob = getMyself();
        if (!aggro) {
            if (gob != null) {
                do {
                    aggro = isAggroed();
                    if (aggro) break;
                    gob = getMyself();
                    moving = getGobPoses(gob, "rowing");
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                } while (moving);
                log.trace("Character stopped.");
            }
        }
    }
}

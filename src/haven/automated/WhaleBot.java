package haven.automated;

import haven.Composite;
import haven.Label;
import haven.Window;
import haven.*;
import haven.botengine.discord.Discord;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;

import static haven.OCache.posres;
import static java.lang.System.out;

public class WhaleBot extends Window implements Runnable {
    private int checkClock;
    public static GameUI gui;
    //public final Thread t;
    public boolean stop;
    private final MCache mcache;
    private static final int MOVEMENT_ACCURACY = 8;
    private int clockwiseDirection = 1;
    public Discord discord;
    private double ang = 0.0;
    private final double searchRadius = 5;
    private final HashSet<Long> seenGobs = new HashSet();
    private ArrayList<Gob> nearbyGobs = new ArrayList();
    private final Random random = new Random();
    private HashSet<String> dangerGobs = new HashSet<String>(Arrays.asList("/boar", "badger", "/adder", "/wolverine", "/walrus", "/lynx", "bear", "/bat", "/wildgoat"));
    private int successLocs;
    private boolean foundFlotsam = false;
    private Gob whaleGob = null;
    private Gob flotsamGob = null;
    private Gob orcaGob = null;

    private Gob greysealGob = null;
    private Gob walrusGob = null;
    private Gob knarrGob = null;
    private Gob snekkjaGob = null;
    private Gob dockGob = null;
    private int a = 0;
    private double h = 0;
    private Coord2d start = null;
    private boolean active = false;
    private boolean smallaggresive = false;
    private boolean diagonalbox = false;
    private boolean eastbox = false;
    private boolean westbox = false;
    private boolean northbox = false;
    ///
    private boolean biglinebox = false;
    private boolean westline = false;
    private boolean eastline = false;
    ///
    private boolean southbox = false;
    private boolean reverse = false;
    private boolean timeback = false;
    private boolean minimap = false;
    private boolean greysealcheck = false;
    private int time = 0;
    private int mime = 0;
    private int DockStart = 0;
    private Coord2d timeisback = null;
    private Coord2d almostback = null;
    private Coord2d minimapsback = null;
    private Coord2d dist = null;
    private Coord2d imhere = null;
    private int tryes = 0;
    private int newa=  0;
    private int toStart = 0;
    private int cacha = 0;

    // ADDED WAY --
    private boolean northeastbox = false;
    private boolean northwestbox = false;
    private ArrayList<Integer> ignorewalrus = new ArrayList();
    private ArrayList<Integer> ignoregreyseal = new ArrayList<>();
// ADDED WAY --

    TextEntry minimaps;
    TextEntry maps;
    TextEntry mins;
    TextEntry x;
    TextEntry y;
    Label mapsmins;
    Label mminips;
    Label Xxx;
    Label Yyy;
    CheckBox DiagonalBox;
    CheckBox MapBox;



    public WhaleBot(GameUI gui) {
        super(UI.scale(UI.scale(280, 150)), "Whale Finder");
        this.gui = gui;
        this.discord = JOGLPanel.discordjava; // если нужн   о будет вернуть аларм в дс на китов "this.discord = JOGLPanel.discord;" если нужно будет вернуть нормальную "this.discord = discord;"

        //this.t = new Thread(this, "WhaleBot");
        this.checkClock = 0;
        this.stop = false;

        this.mcache = gui.map.glob.map;
        this.seenGobs.add(gui.map.player().id);
        CheckBox dirBox = new CheckBox("Sail Clockwise?"){
            {
                this.a = true;
            }

            public void set(boolean val) {
                WhaleBot.this.clockwiseDirection = val ? 1 : -1;
                this.a = val;
            }
        };
        this.add(dirBox);
        CheckBox activeBox = new CheckBox("Active"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.active = val;
                this.a = val;
            }
        };
        this.add(activeBox, UI.scale(0, 30));
        //
        CheckBox TimeBackBox = new CheckBox("TimeBack (Islands only)"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.timeback = val;
                this.a = val;
            }
        };
        this.add(TimeBackBox, UI.scale(0, 60));

        CheckBox aggresive = new CheckBox("Aggresive stop"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.smallaggresive = val;
                this.a = val;
            }
        };
        this.add(aggresive, UI.scale(150, 135));

        CheckBox greysealbox = new CheckBox("Alarm grey seals"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.greysealcheck = val;
                this.a = val;
            }
        };
        this.add(greysealbox, UI.scale(150, 120));

        mins = new TextEntry(50, "0"){
            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE) {
                    return buf.key(ev);
                } else if (ev.getKeyChar() == '\n') {
                    return true;
                }
                return (false);
            }
        };
        this.add(mins, UI.scale(0,85));

        mapsmins = new Label("minutes");
        this.add(mapsmins, 53,85);
        //
        MapBox = new CheckBox("BackToDock"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.minimap = val;
                this.a = val;
            }
        };
        this.add(MapBox, UI.scale(0, 110));

        minimaps = new TextEntry(50, "0"){
            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE) {
                    return buf.key(ev);
                } else if (ev.getKeyChar() == '\n') {
                    return true;
                }
                return (false);
            }
        };
        this.add(minimaps, UI.scale(0,130));

        x = new TextEntry(30, "0"){
            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE || ev.getKeyChar() == KeyEvent.VK_MINUS) {
                    return buf.key(ev);
                } else if (ev.getKeyChar() == '\n') {
                    return true;
                }
                return (false);
            }
        };
        this.add(x, UI.scale(90,110));
        Xxx = new Label("X");
        this.add(Xxx, UI.scale(123,112));

        y = new TextEntry(30, "0"){
            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE || ev.getKeyChar() == KeyEvent.VK_MINUS) {
                    return buf.key(ev);
                } else if (ev.getKeyChar() == '\n') {
                    return true;
                }
                return (false);
            }
        };
        this.add(y, UI.scale(90,130));
        Yyy = new Label("Y");
        this.add(Yyy,UI.scale(123, 132));

        mminips = new Label("grids");
        this.add(mminips, UI.scale(53,132));
        //

        DiagonalBox = new CheckBox("Diagonal moving"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.diagonalbox = val;
                this.a = val;
            }
        };
        this.add(DiagonalBox, UI.scale(140,0));

        CheckBox Eastbox = new CheckBox(""){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.eastbox = val;
                this.a = val;
            }
        };
        this.add(Eastbox, UI.scale(140,20));
        // - west
        CheckBox Westbox = new CheckBox(""){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.westbox = val;
                this.a = val;
            }
        };
        this.add(Westbox, UI.scale(160,20));
        // - north
        CheckBox Northbox = new CheckBox(""){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.northbox = val;
                this.a = val;
            }
        };
        this.add(Northbox, UI.scale(180,20));
        // - south
        CheckBox Southbox = new CheckBox(""){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.southbox = val;
                this.a = val;
            }
        };
        this.add(Southbox, UI.scale(200,20));

        CheckBox Reversebox = new CheckBox(""){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.reverse = val;
                this.a = val;
            }
        };
        this.add(Reversebox, UI.scale(140,48));

        CheckBox lineWestBox = new CheckBox("W Line"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.westline = val;
                this.a = val;
            }
        };
        this.add(lineWestBox, UI.scale(140,63));

        CheckBox lineEastBox = new CheckBox("E Line"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.eastline = val;
                this.a = val;
            }
        };
        this.add(lineEastBox, UI.scale(140,78));

        CheckBox Biglinebox = new CheckBox("Turn line"){
            {
                this.a = false;
            }

            public void set(boolean val) {
                WhaleBot.this.biglinebox = val;
                this.a = val;
            }
        };
        this.add(Biglinebox, UI.scale(200,71));

        Label e = new Label("R");
        this.add(e, UI.scale(142,32));

        Label w = new Label("L");
        this.add(w, UI.scale(162,32));

        Label n = new Label("U");
        this.add(n, UI.scale(182,32));

        Label s = new Label("D");
        this.add(s, UI.scale(202,32));

        Label r = new Label("Reverse");
        this.add(r,UI.scale(160,48));

        //TextEntry maps = createEntry(30, "", 220, 40);
        maps = new TextEntry(30, "0"){
            public boolean keydown(KeyEvent ev) {
                if ((ev.getKeyChar() >= KeyEvent.VK_0 && ev.getKeyChar() <= KeyEvent.VK_9) || ev.getKeyChar() == '\b' || ev.getKeyChar() == KeyEvent.VK_DELETE) {
                    return buf.key(ev);
                } else if (ev.getKeyChar() == '\n') {
                    return true;
                }
                return (false);
            }
        };
        this.add(maps, UI.scale(225,20));

        Label mmaps = new Label("Grids Q");
        this.add(mmaps, UI.scale(225, 40));

        this.gui.adda(this,gui.sz.div(2),0.5,0.5);
    }

    @Override
    public void run() {
        Coord2d repairCoord = gui.map.player().rc;
        try {
            while (!this.stop) {
                if (!this.active && !this.diagonalbox && !this.biglinebox) {
                    Thread.sleep(200);
                    continue;
                }
                if (this.active) {
                    while(this.active){
                        if (this.active && timeback && time == 0){
                            this.timeisback = gui.map.player().rc;
                            time++;
                        }
                        this.flotsamGob = null;
                        this.whaleGob = null;
                        this.orcaGob = null;
                        this.walrusGob = null;
                        this.greysealGob = null;
                        this.flotsamGob = this.checkFlotsam();
                        this.whaleGob = this.checkWhale();
                        this.orcaGob = this.checkOrca();
                        this.walrusGob = this.checkWalrus();
                        this.greysealGob = this.checkgrey();
                        if (this.flotsamGob != null) {
                            pickflotsam();
                        }
                        if (this.whaleGob != null) {
                            out.println("I FOUND WHALE, TRYING TO CLICK");
                            clickWhale();
                        }
                        if (this.orcaGob != null){
                            if (this.newa == 0){
                                discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                discordSendScreen("res/saved.png");
                                this.newa++;
                            }
                            while (this.tryes != 1) {
                                if (this.tryes == 1) break;
                                Defer.later(() -> {
                                    Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                    return (null);
                                });
                                this.tryes++;
                            }
                            close();
                            Thread.sleep(200);
                        }
                        if (this.walrusGob != null && !this.ignorewalrus.contains((int) gobtoId(this.walrusGob))){
                            this.ignorewalrus.add((int) gobtoId(this.walrusGob));
                                discordSay("<@290410960286384129>, <@219213705919987712> I FOUND WALRUS ON ACCOUNT - " + MainFrame.tent);
                                discordSendScreen("res/saved.png");
                            while (this.tryes != 1) {
                                if (this.tryes == 1) break;
                                Defer.later(() -> {
                                    Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                    return (null);
                                });
                                this.tryes++;
                            }
                            if (this.smallaggresive) {
                                close();
                            }
                            Thread.sleep(200);
                        }
                        if (greysealcheck && this.greysealGob != null && !this.ignoregreyseal.contains((int) gobtoId(this.greysealGob))){
                            this.ignoregreyseal.add((int) gobtoId(this.greysealGob));
                            discordSay("<@219213705919987712> I FOUND GREY SEAL ON ACCOUNT - " + MainFrame.tent);
                            discordSendScreen("res/saved.png");
                            while (this.tryes != 1) {
                                if (this.tryes == 1) break;
                                Defer.later(() -> {
                                    Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                    return (null);
                                });
                                this.tryes++;
                            }
                            Thread.sleep(200);
                        }
                        if (this.active) {
                            if (this.timeback && this.active){
                                this.almostback = gui.map.player().rc;
                                double distanceLeft;
                                distanceLeft = gui.map.player().rc.dist(this.timeisback);
                                if (distanceLeft <= 50.0 && this.toStart > 50) {
                                    while (distanceLeft > 0 && this.active) {
                                        out.println(distanceLeft);
                                        distanceLeft = gui.map.player().rc.dist(repairCoord);
                                        if (this.active && this.timeback) {
                                            Thread.sleep(100L);
                                            moveToNoPF(repairCoord);
                                            if (distanceLeft == 0) {
                                                out.println(distanceLeft);
                                                int mmins = Integer.parseInt(mins.text());
                                                if (mmins <= 21) {
                                                    out.println("I'm afking: " + (21 - mmins) + " minutes");
                                                    sysMsg(ui,"I'm afking: " + (21 - mmins) + " minutes", Color.yellow);
                                                    Thread.sleep((21 * 60000) - (mmins * 60000));
                                                    BacktoLastCoord(this.timeisback);
                                                    this.toStart = 0;
                                                    break;
                                                }
                                                if (mmins > 21){
                                                    out.println("Not AFKing");
                                                    sysMsg(ui,"Not AFKing", Color.yellow);
                                                    BacktoLastCoord(this.timeisback);
                                                    this.toStart = 0;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                this.toStart++;
                            }
                            if (this.active && minimap && mime == 0){
                                this.minimapsback = gui.map.player().rc;
                                this.minimapsback = this.minimapsback.add(0.0, 0.0);
                                mime++;
                            }
                            if (this.active && minimap){
                                if (this.DockStart == 0){
                                    double distanceLeft;
                                    Coord2d player = gui.map.player().rc;
                                    int Xx = Integer.parseInt(x.text());
                                    int Yy = Integer.parseInt(y.text());
                                    Coord2d toStart = player.add(0.0, 0.0);
                                    toStart = player.add(Xx * 11,Yy * 11);
                                    distanceLeft = gui.map.player().rc.dist(toStart);
                                    if (distanceLeft > 0 && this.active && minimap) {
                                        while (distanceLeft > 0 && this.active && minimap) {
                                            out.println(distanceLeft);
                                            distanceLeft = gui.map.player().rc.dist(toStart);
                                            if (distanceLeft > 0 && this.active && minimap) {
                                                Thread.sleep(100L);
                                                moveToNoPF(toStart);
                                                if (distanceLeft == 0) {
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    this.DockStart++;
                                }
                                this.imhere = gui.map.player().rc;
                                this.imhere = this.imhere.add(0.0, 0.0);
                                double distanceLeft;
                                int minimapser = Integer.parseInt(minimaps.text());
                                int mmins = Integer.parseInt(mins.text());
                                minimapser = minimapser * 1100;
                                distanceLeft = minimapsback.dist(this.imhere);
                                if (minimapser <= distanceLeft){
                                    portToDock();
                                    if (mmins <= 21) {
                                        this.imhere = gui.map.player().rc;
                                        mime--;
                                        Coord2d player = gui.map.player().rc;
                                        int Xx = Integer.parseInt(x.text());
                                        int Yy = Integer.parseInt(y.text());
                                        Coord2d toStart = player.add(0.0, 0.0);
                                        toStart = player.add(Xx * 11,Yy * 11);
                                        distanceLeft = gui.map.player().rc.dist(toStart);
                                        if (distanceLeft > 0 && this.active && minimap) {
                                            while (distanceLeft > 0 && this.active && minimap) {
                                                out.println(distanceLeft);
                                                distanceLeft = gui.map.player().rc.dist(toStart);
                                                if (distanceLeft > 0 && this.active && minimap) {
                                                    Thread.sleep(100L);
                                                    moveToNoPF(toStart);
                                                    if (distanceLeft == 0) {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        out.println("I'm afking: " + (21 - mmins) + " minutes");
                                        sysMsg(ui,"I'm afking: " + (21 - mmins) + " minutes", Color.yellow);
                                        Thread.sleep((21 * 60000) - (mmins * 60000));
                                    }
                                    if (mmins > 21){
                                        this.imhere = gui.map.player().rc;
                                        this.imhere = this.imhere.add(0.0, 0.0);
                                        mime--;
                                        out.println("Not AFKing");
                                        sysMsg(ui,"Not AFKing", Color.yellow);
                                        Coord2d player = gui.map.player().rc;
                                        int Xx = Integer.parseInt(x.text());
                                        int Yy = Integer.parseInt(y.text());
                                        Coord2d toStart = player.add(Xx * 11,Yy * 11);
                                        distanceLeft = gui.map.player().rc.dist(toStart);
                                        if (distanceLeft > 0 && this.active && minimap) {
                                            while (distanceLeft > 0 && this.active && minimap) {
                                                out.println(distanceLeft);
                                                distanceLeft = gui.map.player().rc.dist(toStart);
                                                if (distanceLeft > 0 && this.active && minimap) {
                                                    Thread.sleep(100L);
                                                    moveToNoPF(toStart);
                                                    if (distanceLeft == 0) {
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (this.gui == null || !ui.sess.alive()) {
                                this.stop = true;
                                this.stop();
                                this.reqdestroy();
                                gui.OrcaFinderThread.interrupt();
                                break;
                            } else {
                                if (this.successLocs > 20) {
                                    final Coord2d groundTile = this.findRandomGroundTile();
                                    Coord2d groundVector = groundTile.sub(this.gui.map.player().rc);
                                    groundVector = groundVector.div(groundVector.abs()).mul(44.0);
                                    this.gui.map.wdgmsg("click", new Object[]{Coord.z, this.gui.map.player().rc.add(groundVector).floor(OCache.posres), 1, 0});
                                    Thread.sleep(300L);
                                }
                                this.nearbyGobs = this.getNearbyGobs();
                                final Coord loc = this.getNextLoc();
                                if (loc != null) {
                                    this.ang -= this.clockwiseDirection * Math.PI / 2.0;
                                    this.gui.map.wdgmsg("click", new Object[]{Coord.z, new Coord2d((double) loc.x, (double) loc.y).floor(OCache.posres), 1, 0});

                                } else {
                                    final Coord2d pcCoord = this.gui.map.player().rc;
                                    final Coord2d gocoord = this.findRandomWaterTile();
                                    Coord2d groundVector2 = gocoord.sub(this.gui.map.player().rc);
                                    groundVector2 = groundVector2.div(groundVector2.abs()).mul(33.0);
                                    this.gui.map.wdgmsg("click", new Object[]{Coord.z, this.gui.map.player().rc.add(groundVector2).floor(OCache.posres), 1, 0});
                                    Thread.sleep(300L);
                                }
                            }
                        }
                        if (!this.active){
                            Coord2d playerCoord = gui.map.player().rc;
                            ui.gui.map.wdgmsg("click", Coord.z, ui.gui.map.player().rc.floor(posres), 1, 0);
                        }
                        Thread.sleep(200L);
                    }
                }
                if (this.diagonalbox) {
                    sysMsg(ui,"diagonal box turned on", Color.CYAN);
                    out.println("diagonal box turned on");
                    double distanceLeft;
                    if (this.diagonalbox) {
                        this.knarrGob = null;
                        this.knarrGob = this.getKnarr();
                        if (this.knarrGob == null){
                            sysMsg(ui,"No knarr, diagonal moving turned off", Color.red);
                            out.println("No knarr, diagonal moving turned off");
                            DiagonalBox.uimsg("ch", 0);
                            this.diagonalbox = false;
                        }
                        if (this.knarrGob != null) {
                            this.a = getKnarrHp(ui);
                            if (this.a == 0) {
                                this.a = getKnarrHp(ui);
                                Thread.sleep(200L);
                                continue;
                            }
                        }
                        this.knarrGob = this.getKnarr();
                        outer:
                        while (this.diagonalbox) {
                            Coord2d playerCoord = gui.map.player().rc;
                            Coord2d orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            int mmaps = Integer.parseInt(maps.text());

                            if (mmaps == -1 || mmaps == 0){
                                sysMsg(ui,"Cannot run bot bcs wrong number in Grids Q", Color.red);
                                out.println("Cannot run bot bcs wrong number in Grids Q");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && this.northbox){
                                sysMsg(ui,"Cannot run bot bcs it is north checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.eastbox && this.southbox) {
                                sysMsg(ui,"Cannot run bot bcs it is south checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.northbox && westbox){
                                this.start = playerCoord.add(-mmaps * 1100, -mmaps * 1100);
                            }
                            if (this.southbox && this.westbox){
                                this.start = playerCoord.add(-mmaps * 1100, mmaps * 1100);
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && !this.northbox || this.eastbox && !this.southbox) {
                                this.start = playerCoord.add(mmaps * 1100, mmaps * 1100);
                            }
                            if (this.westbox && !this.northbox){
                                this.start = playerCoord.add(-mmaps * 1100, mmaps * 1100);
                            }
                            if (this.northbox && !this.westbox){
                                this.start = playerCoord.add(mmaps * 1100, -mmaps * 1100);
                            }
                            if (this.southbox && !this.westbox){
                                this.start = playerCoord.add(mmaps * 1100, mmaps * 1100);
                            }
                            distanceLeft = gui.map.player().rc.dist(this.start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            if (this.gui == null || !ui.sess.alive()) {
                                this.stop = true;
                                this.stop();
                                this.reqdestroy();
                                gui.OrcaFinderThread.interrupt();
                                break;
                            }
                            orcabackCoord = gui.map.player().rc;
                            while (distanceLeft > 0 && this.diagonalbox) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.diagonalbox) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.knarrGob = this.getKnarr();
                                    if (this.knarrGob != null) {
                                        this.a = this.getKnarrHp(ui);
                                    }
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (this.knarrGob != null) {
                                        if (this.a <= 40) {
                                            Coord2d backCoord = gui.map.player().rc;
                                            this.backRepairKnarr(repairCoord, backCoord);
                                        }
                                    }
                                    if (this.flotsamGob != null) {
                                        this.pickflotsam();
                                    }
                                    if (this.whaleGob != null) {
                                        this.clickWhale();
                                    }
                                    if (this.orcaGob != null){
                                        if (this.newa == 0){
                                            discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                            discordSendScreen("res/saved.png");
                                            this.newa++;
                                        }
                                        while (this.tryes != 1) {
                                            if (this.tryes == 1) break;
                                            Defer.later(() -> {
                                                Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                                return (null);
                                            });
                                            this.tryes++;
                                        }
                                        BacktoLastCoord(orcabackCoord);
                                        break;
                                    }
                                    if (distanceLeft == 0) {
                                        break;
                                    }
                                }
                            }

                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            mmaps = Integer.parseInt(maps.text());
                            if (mmaps == -1 || mmaps == 0){
                                sysMsg(ui,"Cannot run bot bcs wrong number in Grids Q", Color.red);
                                out.println("Cannot run bot bcs wrong number in Grids Q");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && this.northbox){
                                sysMsg(ui,"Cannot run bot bcs it is north checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.eastbox && this.southbox) {
                                sysMsg(ui,"Cannot run bot bcs it is south checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }

                            if (this.northbox && this.westbox && !this.reverse){
                                this.start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.southbox && this.westbox && !this.reverse){
                                this.start = playerCoord.add(0.0, 1804.0);
                            }

                            //reverse
                            if (this.northbox && this.westbox && this.reverse){
                                this.start = playerCoord.add(0.0, 1804.0);
                            }
                            if (this.southbox && this.westbox && this.reverse){
                                this.start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.eastbox && !this.northbox && !this.southbox && this.reverse) {
                                start = playerCoord.add(-1804.0, 0.0);
                            }
                            if (this.westbox && !this.northbox && !this.southbox && this.reverse){
                                start = playerCoord.add(1804.0, 0.0);
                            }
                            if (this.northbox && !westbox && this.reverse){
                                start = playerCoord.add(0.0, 1804.0);
                            }
                            if (this.southbox && !this.westbox && this.reverse){
                                start = playerCoord.add(0.0, -1804.0);
                            }
                            //reverse

                            if (this.northbox && this.westbox && !this.reverse){
                                this.start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.southbox && this.westbox && !this.reverse){
                                this.start = playerCoord.add(0.0, 1804.0);
                            }
//
                            if (this.eastbox && !this.northbox && !this.southbox && !this.reverse) {
                                start = playerCoord.add(1804.0, 0.0);
                            }
                            if (this.westbox && !this.northbox && !this.southbox && !this.reverse){
                                start = playerCoord.add(-1804.0, 0.0);
                            }
                            if (this.northbox && !this.westbox && !this.reverse){
                                start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.southbox && !this.westbox && !this.reverse){
                                start = playerCoord.add(0.0, 1804.0);
                            }
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.diagonalbox) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.diagonalbox) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.knarrGob = this.getKnarr();
                                    if (this.knarrGob != null) {
                                        this.a = this.getKnarrHp(ui);
                                    }
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (this.knarrGob != null) {
                                        if (this.a <= 40) {
                                            Coord2d backCoord = gui.map.player().rc;
                                            this.backRepairKnarr(repairCoord, backCoord);
                                        }
                                    }
                                    if (this.flotsamGob != null) {
                                        this.pickflotsam();
                                    }
                                    if (this.whaleGob != null) {
                                        this.clickWhale();
                                    }
                                    if (this.orcaGob != null){
                                        if (this.newa == 0){
                                            discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                            discordSendScreen("res/saved.png");
                                            this.newa++;
                                        }
                                        while (this.tryes != 1) {
                                            if (this.tryes == 1) break;
                                            Defer.later(() -> {
                                                Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                                return (null);
                                            });
                                            this.tryes++;
                                        }
                                        BacktoLastCoord(orcabackCoord);
                                        BackToRepairPlace(repairCoord, this.diagonalbox);
                                        this.diagonalbox = false;
                                        wdgmsg("ch", this.diagonalbox, 0);
                                        break;
                                    }

                                    if (distanceLeft == 0) {
                                        break;
                                    }
                                }
                            }
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;


                            if (mmaps == -1 || mmaps == 0){
                                sysMsg(ui,"Cannot run bot bcs wrong number in Grids Q", Color.red);
                                out.println("Cannot run bot bcs wrong number in Grids Q");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && this.northbox){
                                sysMsg(ui,"Cannot run bot bcs it is north checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.eastbox && this.southbox) {
                                sysMsg(ui,"Cannot run bot bcs it is south checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && this.northbox){
                                sysMsg(ui,"Cannot run bot bcs it is north checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.eastbox && this.southbox) {
                                sysMsg(ui,"Cannot run bot bcs it is south checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.northbox && this.westbox){
                                this.start = playerCoord.add(mmaps * 1100, mmaps * 1100);
                            }
                            if (this.southbox && this.westbox && !this.northbox && !this.eastbox){
                                this.start = playerCoord.add(mmaps * 1100, -mmaps * 1100);
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && !this.northbox && !this.southbox) {
                                this.start = playerCoord.add(-mmaps * 1100, -mmaps * 1100);
                            }
                            if (this.westbox && !this.northbox && !this.southbox){
                                this.start = playerCoord.add(mmaps * 1100, -mmaps * 1100);
                            }
                            if (this.northbox && !westbox){
                                this.start = playerCoord.add(-mmaps * 1100, mmaps * 1100);
                            }
                            if (this.southbox && !this.westbox){
                                this.start = playerCoord.add(-mmaps * 1100, -mmaps * 1100);
                            }
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.diagonalbox) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.diagonalbox) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.knarrGob = this.getKnarr();
                                    if (this.knarrGob != null) {
                                        this.a = this.getKnarrHp(ui);
                                    }
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (this.knarrGob != null) {
                                        if (this.a <= 40) {
                                            Coord2d backCoord = gui.map.player().rc;
                                            this.backRepairKnarr(repairCoord, backCoord);
                                        }
                                    }
                                    if (this.flotsamGob != null) {
                                        this.pickflotsam();
                                    }
                                    if (this.whaleGob != null) {
                                        this.clickWhale();
                                    }
                                    if (this.orcaGob != null){
                                        if (this.newa == 0){
                                            discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                            discordSendScreen("res/saved.png");
                                            this.newa++;
                                        }
                                        while (this.tryes != 1) {
                                            if (this.tryes == 1) break;
                                            Defer.later(() -> {
                                                Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                                return (null);
                                            });
                                            this.tryes++;
                                        }
                                        BacktoLastCoord(orcabackCoord);
                                        break;
                                    }
                                    if (distanceLeft == 0) {
                                        break;
                                    }
                                }
                            }
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            if (mmaps == -1 || mmaps == 0){
                                sysMsg(ui,"Cannot run bot bcs wrong number in Grids Q", Color.red);
                                out.println("Cannot run bot bcs wrong number in Grids Q");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            // -- OTHER MOVEMENTS
                            if (this.eastbox && this.northbox){
                                sysMsg(ui,"Cannot run bot bcs it is north checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.eastbox && this.southbox) {
                                sysMsg(ui,"Cannot run bot bcs it is south checkbox", Color.red);
                                out.println("Cannot run bot bcs it is south checkbox");
                                DiagonalBox.uimsg("ch", 0);
                                this.diagonalbox = false;
                            }
                            if (this.northbox && this.westbox && !this.reverse){
                                this.start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.southbox && this.westbox && !this.reverse){
                                this.start = playerCoord.add(0.0, 1804.0);
                            }
                            //reverse
                            if (this.northbox && this.westbox && this.reverse){
                                this.start = playerCoord.add(0.0, 1804.0);
                            }
                            if (this.southbox && this.westbox && this.reverse){
                                this.start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.eastbox && !this.northbox && !this.southbox && this.reverse) {
                                start = playerCoord.add(-1804.0, 0.0);
                            }
                            if (this.westbox && !this.northbox && !this.southbox && this.reverse){
                                start = playerCoord.add(1804.0, 0.0);
                            }
                            if (this.northbox && !westbox && this.reverse){
                                start = playerCoord.add(0.0, 1804.0);
                            }
                            if (this.southbox && !this.westbox && this.reverse){
                                start = playerCoord.add(0.0, -1804.0);
                            }
                            //reverse
                            // -- OTHER MOVEMENTS

                            if (this.eastbox && !this.northbox && !this.southbox && !this.reverse) {
                                start = playerCoord.add(1804.0, 0.0);
                            }
                            if (this.westbox && !this.northbox && !this.southbox && !this.reverse){
                                start = playerCoord.add(-1804.0, 0.0);
                            }
                            if (this.northbox && !westbox && !this.reverse){
                                start = playerCoord.add(0.0, -1804.0);
                            }
                            if (this.southbox && !this.westbox && !this.reverse){
                                start = playerCoord.add(0.0, 1804.0);
                            }
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.diagonalbox) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.diagonalbox) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.knarrGob = this.getKnarr();
                                    if (this.knarrGob != null) {
                                        this.a = this.getKnarrHp(ui);
                                    }
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (this.knarrGob != null) {
                                        if (this.a <= 40) {
                                            Coord2d backCoord = gui.map.player().rc;
                                            this.backRepairKnarr(repairCoord, backCoord);
                                        }
                                    }
                                    if (this.flotsamGob != null) {
                                        this.pickflotsam();
                                    }
                                    if (this.whaleGob != null) {
                                        this.clickWhale();
                                    }
                                    if (this.orcaGob != null){
                                        if (this.newa == 0){
                                            discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                            discordSendScreen("res/saved.png");
                                            this.newa++;
                                        }
                                        while (this.tryes != 1) {
                                            if (this.tryes == 1) break;
                                            Defer.later(() -> {
                                                Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                                return (null);
                                            });
                                            this.tryes++;
                                        }
                                        BacktoLastCoord(orcabackCoord);
                                        BackToRepairPlace(repairCoord, this.diagonalbox);
                                        this.diagonalbox = false;
                                        wdgmsg("ch", this.diagonalbox, 0);
                                        break;
                                    }
                                    if (distanceLeft == 0) {
                                        break;
                                    }
                                }
                            }
                            if (!this.diagonalbox){
                                this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                            }
                        }
                    }
                }
                if (!this.biglinebox){
                    Thread.sleep(200L);
                    continue;
                }
                if (this.biglinebox){
                    Coord2d playerCoord = gui.map.player().rc;
                    Coord2d orcabackCoord = gui.map.player().rc;
                    this.start = playerCoord;
                    double distanceLeft;
                    if (this.eastline && !this.westline){
                        if (this.eastline && !this.westline && this.biglinebox) {
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(0, -19 * 11);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.eastline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.eastline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.eastline){
                                        this.eastline = false;
                                        wdgmsg("ch", this.eastline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.eastline = false;
                                    wdgmsg("ch", this.eastline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                        if (this.eastline && !this.westline && this.biglinebox) {
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(18 * 11, 0);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.eastline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.eastline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.eastline){
                                        this.eastline = false;
                                        wdgmsg("ch", this.eastline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });

                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.eastline = false;
                                    wdgmsg("ch", this.eastline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                        if (this.eastline && !this.westline && this.biglinebox) {
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(0, 19 * 11);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.eastline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.eastline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.eastline){
                                        this.eastline = false;
                                        wdgmsg("ch", this.eastline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.eastline = false;
                                    wdgmsg("ch", this.eastline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                        if (this.eastline && !this.westline && this.biglinebox) {
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(82 * 11, 0);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.eastline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.eastline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.eastline){
                                        this.eastline = false;
                                        wdgmsg("ch", this.eastline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.eastline = false;
                                    wdgmsg("ch", this.eastline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                    }
                    if (this.westline && !this.eastline && this.biglinebox){
                        playerCoord = gui.map.player().rc;
                        orcabackCoord = gui.map.player().rc;
                        this.start = playerCoord;
                        if (this.westline){
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(0, -19 * 11);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.westline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.westline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.westline){
                                        this.westline = false;
                                        wdgmsg("ch", this.westline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.westline = false;
                                    wdgmsg("ch", this.westline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                        if (this.westline && !this.eastline && this.biglinebox){
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(-18 * 11, 0);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.westline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.westline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.westline){
                                        this.westline = false;
                                        wdgmsg("ch", this.westline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.westline = false;
                                    wdgmsg("ch", this.westline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                        if (this.westline && !this.eastline && this.biglinebox){
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(0, 19 * 11);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.westline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.westline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.westline){
                                        this.westline = false;
                                        wdgmsg("ch", this.westline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.westline = false;
                                    wdgmsg("ch", this.westline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                        if (this.westline && !this.eastline && this.biglinebox){
                            playerCoord = gui.map.player().rc;
                            orcabackCoord = gui.map.player().rc;
                            this.start = playerCoord;
                            this.start = playerCoord.add(-82 * 11, 0);
                            distanceLeft = gui.map.player().rc.dist(start);
                            //moveToNoPF(start);
                            out.println(distanceLeft);
                            while (distanceLeft > 0 && this.westline) {
                                out.println(distanceLeft);
                                distanceLeft = gui.map.player().rc.dist(start);
                                if (this.westline) {
                                    Thread.sleep(100L);
                                    moveToNoPF(start);
                                    this.flotsamGob = null;
                                    this.whaleGob = null;
                                    this.orcaGob = null;
                                    this.walrusGob = null;
                                    this.orcaGob = this.checkOrca();
                                    this.whaleGob = this.checkWhale();
                                    this.flotsamGob = this.checkFlotsam();
                                    if (this.gui == null || !ui.sess.alive()) {
                                        this.stop = true;
                                        this.stop();
                                        this.reqdestroy();
                                        gui.OrcaFinderThread.interrupt();
                                        break;
                                    }
                                    if (!this.westline){
                                        this.westline = false;
                                        wdgmsg("ch", this.westline, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                    if (!this.biglinebox){
                                        this.biglinebox = false;
                                        wdgmsg("ch", this.biglinebox, 0);
                                        this.gui.map.wdgmsg("click", new Object[] { Coord.z, this.gui.map.player().rc.floor(OCache.posres), 1, 0 });
                                        break;
                                    }
                                }
                                if (this.flotsamGob != null) {
                                    this.pickflotsam();
                                }
                                if (this.whaleGob != null) {
                                    this.clickWhale();
                                }
                                if (this.orcaGob != null){
                                    if (this.newa == 0){
                                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                                        discordSendScreen("res/saved.png");
                                        this.newa++;
                                    }
                                    while (this.tryes != 1) {
                                        if (this.tryes == 1) break;
                                        Defer.later(() -> {
                                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                                            return (null);
                                        });
                                        this.tryes++;
                                    }
                                    this.westline = false;
                                    wdgmsg("ch", this.westline, 0);
                                    break;
                                }
                                if (distanceLeft == 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(InterruptedException e){
            out.println("interrupted..");
        }
    }
    /*
    public String getText() {
        out.println(maps);
        return this.maps;
    }

     */

    public static void sysMsg(UI ui, String str, Color col) {
        if (gui != null)
            gui.msg(str, col);
    }


    public void doAct(UI ui, String act) {
        gui.menu.wdgmsg("act", act);
    }

    public double getHourglass() {
        return gui.prog.prog;
    }

    public Discord getDiscord() {
        return discord;
    }

    public void discordSay(String message) {
        getDiscord().sendMessage(message);
    }

    public void BackToRepairPlace(Coord2d coords, boolean name) throws InterruptedException {
        double distanceLeft;
        moveToNoPF(coords);
        distanceLeft = gui.map.player().rc.dist(coords);
        while (distanceLeft > 0 && name) {
            distanceLeft = gui.map.player().rc.dist(coords);
            out.println(distanceLeft);
            distanceLeft = gui.map.player().rc.dist(coords);
            if (name) {
                Thread.sleep(100L);
                moveToNoPF(coords);
                this.flotsamGob = null;
                this.whaleGob = null;
                this.orcaGob = null;
                this.walrusGob = null;
                this.orcaGob = this.checkOrca();
                this.whaleGob = this.checkWhale();
                this.flotsamGob = this.checkFlotsam();
                if (this.flotsamGob != null) {
                    this.pickflotsam();
                }
                if (this.whaleGob != null) {
                    this.clickWhale();
                }
                if (this.orcaGob != null){
                    if (this.newa == 0){
                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                        discordSendScreen("res/saved.png");
                        this.newa++;
                    }
                    while (this.tryes != 1) {
                        if (this.tryes == 1) break;
                        Defer.later(() -> {
                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                            return (null);
                        });
                        this.tryes++;
                    }
                    close();
                    Thread.sleep(200);
                }
                if (distanceLeft == 0) {
                    break;
                }
            }
        }
    }

    public void BacktoLastCoord(Coord2d backcoord) throws InterruptedException {
        double distanceLeft;
        moveToNoPF(backcoord);
        distanceLeft = gui.map.player().rc.dist(backcoord);
        while (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active) {
            distanceLeft = gui.map.player().rc.dist(backcoord);
            out.println(distanceLeft);
            distanceLeft = gui.map.player().rc.dist(backcoord);
            if (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active) {
                Thread.sleep(100L);
                moveToNoPF(backcoord);
                this.flotsamGob = null;
                this.whaleGob = null;
                this.orcaGob = null;
                this.walrusGob = null;
                this.orcaGob = this.checkOrca();
                this.whaleGob = this.checkWhale();
                this.flotsamGob = this.checkFlotsam();
                if (this.flotsamGob != null) {
                    this.pickflotsam();
                }
                if (this.whaleGob != null) {
                    this.clickWhale();
                }
                if (distanceLeft == 0) {
                    break;
                }
            }
        }
    }

    public void backRepairKnarr(Coord2d coords, Coord2d lastcoord) throws InterruptedException {
        double distanceLeft;
        moveToNoPF(coords);
        distanceLeft = gui.map.player().rc.dist(coords);
        while (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active) {
            distanceLeft = gui.map.player().rc.dist(coords);
            out.println(distanceLeft);
            distanceLeft = gui.map.player().rc.dist(coords);
            if (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active) {
                Thread.sleep(100L);
                moveToNoPF(coords);
                this.flotsamGob = null;
                this.whaleGob = null;
                this.orcaGob = null;
                this.walrusGob = null;
                this.orcaGob = this.checkOrca();
                this.whaleGob = this.checkWhale();
                this.flotsamGob = this.checkFlotsam();
                if (this.flotsamGob != null) {
                    this.pickflotsam();
                }
                if (this.whaleGob != null) {
                    this.clickWhale();
                }
                if (this.orcaGob != null){
                    if (this.newa == 0){
                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                        discordSendScreen("res/saved.png");
                        this.newa++;
                    }
                    while (this.tryes != 1) {
                        if (this.tryes == 1) break;
                        Defer.later(() -> {
                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                            return (null);
                        });
                        this.tryes++;
                    }
                    close();
                    Thread.sleep(200L);
                }
                if (distanceLeft == 0) {
                    break;
                }
            }
        }
        this.knarrGob = null;
        this.knarrGob = this.getKnarr();
        if (this.knarrGob != null) {
            doAct(ui, "repair");
            Thread.sleep(200);
            doClick(ui, this.knarrGob, 1, 0);
        }
        Thread.sleep(500);
        this.h = 0;
        this.h = getHourglass();
        while(this.h >= 0.20) {
            this.h = getHourglass();
            out.println(this.h + " Hourglass progress");
            Thread.sleep(50);
            if (this.h == -1 || this.h == 1.00) {
                out.println(this.h + " Hourglass progress ended");
                cancelAct();
                break;
            }
        }
        moveToNoPF(lastcoord);
        while (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active) {
            distanceLeft = gui.map.player().rc.dist(lastcoord);
            out.println(distanceLeft);
            distanceLeft = gui.map.player().rc.dist(lastcoord);
            if (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active) {
                Thread.sleep(100L);
                moveToNoPF(lastcoord);
                this.flotsamGob = null;
                this.whaleGob = null;
                this.orcaGob = null;
                this.walrusGob = null;
                this.orcaGob = this.checkOrca();
                this.whaleGob = this.checkWhale();
                this.flotsamGob = this.checkFlotsam();
                if (this.flotsamGob != null) {
                    this.pickflotsam();
                }
                if (this.whaleGob != null) {
                    this.clickWhale();
                }
                if (this.orcaGob != null){
                    if (this.newa == 0){
                        discordSay("<@&1177320218889048114> I FOUND ORCA ON ACCOUNT - " + MainFrame.tent);
                        discordSendScreen("res/saved.png");
                        this.newa++;
                    }
                    while (this.tryes != 1) {
                        if (this.tryes == 1) break;
                        Defer.later(() -> {
                            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
                            return (null);
                        });
                        this.tryes++;
                    }
                    close();
                    Thread.sleep(200);
                }
                if (distanceLeft == 0) {
                    break;
                }
            }
        }
    }



    public void cancelAct() {
        gui.map.wdgmsg("click", getCenterScreenCoord(ui), new Coord2d(0, 0).floor(posres), 3, 0);
    }

    public Coord getCenterScreenCoord(UI ui) {
        Coord sc, sz;
        sz = gui.map.sz;
        sc = new Coord((int) Math.round(Math.random() * 200 + sz.x / 2f - 100),
                (int) Math.round(Math.random() * 200 + sz.y / 2f - 100));
        return sc;
    }

    public void doClick(UI ui, Gob gob, int button, int mod) {
        gui.map.wdgmsg("click", Coord.z, gob.rc.floor(posres), button, 0, mod, (int) gob.id, gob.rc.floor(posres), 0, -1);
    }

    public void pickflotsam() throws InterruptedException {
        double distanceLeft;
        Coord2d playerCoord = gui.map.player().rc;
        this.flotsamGob = this.checkFlotsam();
        if (this.flotsamGob != null){
            while (this.flotsamGob != null) {
                rightClickGobAndSelectOption(this.gui, this.flotsamGob, "Pick");
                Thread.sleep(500L);
                this.flotsamGob = this.checkFlotsam();
                if (this.flotsamGob == null) break;
            }
        }
        if (this.flotsamGob == null) {
            moveToNoPF(playerCoord);
            distanceLeft = gui.map.player().rc.dist(playerCoord);
            while (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active || distanceLeft > 0 && this.biglinebox) {
                distanceLeft = gui.map.player().rc.dist(playerCoord);
                out.println(distanceLeft);
                distanceLeft = gui.map.player().rc.dist(playerCoord);
                if (distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.active || distanceLeft > 0 && this.biglinebox) {
                    Thread.sleep(100L);
                    moveToNoPF(playerCoord);
                    if (distanceLeft == 0) {
                        break;
                    }
                }
            }
        }
    }

    public void FlowerMenuClick(String action) {
        FlowerMenu menu = gui.ui.root.getchild(FlowerMenu.class);
        if (menu == null) {
            while (true) {
                menu = gui.ui.root.getchild(FlowerMenu.class);
                try {
                    int i = 0;
                    if (i == 2) break;
                    Thread.sleep(200 * i);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        if (menu != null) {
            while (true) {
                menu = gui.ui.root.getchild(FlowerMenu.class);
                try {
                    menu = gui.ui.root.getchild(FlowerMenu.class);
                    if (menu == null) break;
                    menu.choosestring(action);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (menu == null) break;
            }
        }
    }

    public void portToDock() throws InterruptedException {
        this.snekkjaGob = null;
        this.knarrGob = null;
        this.snekkjaGob = getSnekkja();
        this.knarrGob = getKnarr();
        if (snekkjaGob != null){
            gui.map.wdgmsg("click", new Object[] { Coord.z, snekkjaGob.rc.floor(OCache.posres), 3, 0, 0, (int)snekkjaGob.id, snekkjaGob.rc.floor(OCache.posres), 0, -1 });
            Thread.sleep(3000);
            FlowerMenuClick("Travel to home dock");
        }
        if (knarrGob != null){
            gui.map.wdgmsg("click", new Object[] { Coord.z, knarrGob.rc.floor(OCache.posres), 3, 0, 0, (int)knarrGob.id, knarrGob.rc.floor(OCache.posres), 0, -1 });
            Thread.sleep(3000);
            FlowerMenuClick("Travel to home dock");
        }
        this.h = 0;
        this.h = getHourglass();
        while(this.h >= 0) {
            this.h = getHourglass();
            out.println(this.h + " Hourglass progress");
            Thread.sleep(50);
            if (this.h == -1 || this.h == 1.00) {
                out.println(this.h + " Hourglass progress ended");
                cancelAct();
                break;
            }
        }
    }

    public static int getKnarrHp(UI ui) {
        IMeter hp = gui.getmeter("boat");
        if (hp != null) {
            String tt = hp.getmeter();
            tt = tt.substring(tt.indexOf(": ") + 2);
            tt = tt.replace("%","");
            int ttNumber = Integer.parseInt(tt);
            return ttNumber;
        }
        return 0;
    }

    public void clickWhale() throws InterruptedException {
        double distanceLeft;
        this.whaleGob = this.checkWhale();
        if (this.whaleGob != null) {
            this.whaleGob = this.checkWhale();
            if (this.whaleGob != null && this.diagonalbox || this.whaleGob != null && this.active || this.whaleGob != null && this.biglinebox) {
                Coord2d destination = new Coord2d(this.whaleGob.rc.x, this.whaleGob.rc.y);
                if (this.cacha == 0){
                    discordSay("<@&1177320218889048114> FOUND WHALE ON ACCOUNT - " + MainFrame.tent);
                    discordSendScreen("res/saved.png");
                    this.cacha++;
                }
                moveToNoPF(destination);
                destination = new Coord2d(this.whaleGob.rc.x, this.whaleGob.rc.y);
                distanceLeft = gui.map.player().rc.dist(destination);
                while (distanceLeft > 0 && this.active || distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.biglinebox) {
                    destination = new Coord2d(this.whaleGob.rc.x, this.whaleGob.rc.y);
                    distanceLeft = gui.map.player().rc.dist(destination);
                    out.println(distanceLeft);
                    distanceLeft = gui.map.player().rc.dist(destination);
                    if (distanceLeft > 0 && this.active || distanceLeft > 0 && this.diagonalbox || distanceLeft > 0 && this.biglinebox) {
                        Thread.sleep(100L);
                        Audio.play(Loading.waitfor(Resource.remote().load("sfx/borka/whistle")));
                        moveToNoPF(destination);
                        if (distanceLeft == 0) {
                            break;
                        }
                    }
                }
            }
        }
    }


    public void moveToNoPF(Coord2d coordinate) {
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        gui.map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 0);
    }

    public void rightClickGobAndSelectOption(final GameUI gui, final Gob gob, String action) throws InterruptedException {
        gui.map.wdgmsg("click", new Object[] { Coord.z, gob.rc.floor(OCache.posres), 3, 0, 0, (int)gob.id, gob.rc.floor(OCache.posres), 0, -1 });
        Thread.sleep(3000);
        FlowerMenuClick(action);
    }

    public void move(int x, int y) {
        Coord2d playerCoordinate = gui.map.player().rc;
        moveTo((int) playerCoordinate.x + x, (int) playerCoordinate.y + y);
    }


    public void moveTo(Coord2d coordinate) {
        moveTo((int) coordinate.x, (int) coordinate.y);
    }


    public void moveTo(Gob gob) {
        moveTo((int) gob.rc.x, (int) gob.rc.y);
    }


    public void moveTo(int x, int y) {
        Coord2d destination = new Coord2d(x, y);
        double distanceLeft;
        int multiplier = 24;

        do {
            gui.map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 0);
            waitUntilStops();
            distanceLeft = gui.map.player().rc.dist(destination);
            if (distanceLeft > MOVEMENT_ACCURACY) {
                if (multiplier > 400) throw new RuntimeException("Could not move to the given destination");

                out.println("Trying to find another route, distance left {}" + distanceLeft);
                moveWithoutUnblocking(
                        (int) Math.round(Math.random() * multiplier - multiplier / 2.0),
                        (int) Math.round(Math.random() * multiplier - multiplier / 2.0)
                );
                multiplier += 4;
                waitUntilStops();
                distanceLeft = gui.map.player().rc.dist(destination);
            }
        } while (distanceLeft > MOVEMENT_ACCURACY);
    }

    public void waitUntilStops() {
        out.println("Waiting until the character stops.");
        Moving moving;
        Coord2d begin, end;
        do {
            begin = gui.map.player().rc;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            end = gui.map.player().rc;
            moving = gui.map.player().getattr(Moving.class);
        } while (moving != null && begin.dist(end) > 1);
        out.println("Character stopped.");
    }

    private void moveWithoutUnblocking(int x, int y) {
        Coord2d playerCoord = gui.map.player().rc;
        gui.map.wdgmsg("click", Coord.z, new Coord2d(playerCoord.x + x, playerCoord.y + y).floor(posres), 1, 0);
    }

    private void moveWithoutUnblockinggeb(Gob gob) {
        Coord2d playerCoord = gui.map.player().rc;
        gui.map.wdgmsg("click", (int) gob.rc.x, (int) gob.rc.y, 1, 0);
    }

    private Coord2d findRandomGroundTile() {
        final Coord2d basecoord = this.gui.map.player().rc;
        final int radius = 440;
        for (int i = 0; i < 1000; ++i) {
            final Coord2d rancoord = new Coord2d((double)(this.random.nextInt(radius * 2) - radius), (double)(this.random.nextInt(radius * 2) - radius));
            if (!this.isWater(basecoord.add(rancoord).floor())) {
                return basecoord.add(rancoord);
            }
        }
        return basecoord;
    }

    private Coord2d findRandomWaterTile() {
        final Coord2d basecoord = this.gui.map.player().rc;
        final int radius = 330;
        for (int i = 0; i < 1000; ++i) {
            final Coord2d rancoord = new Coord2d((double)(this.random.nextInt(radius * 2) - radius), (double)(this.random.nextInt(radius * 2) - radius));
            if (this.isWater(basecoord.add(rancoord).floor())) {
                return basecoord.add(rancoord);
            }
        }
        return basecoord;
    }

    private ArrayList<Gob> getNearbyGobs() {
        final ArrayList<Gob> gobs = new ArrayList<Gob>();
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (this.gui.map.player().rc.dist(gob.rc) < 3.0) {
                    continue;
                }
                if (this.gui.map.player().rc.dist(gob.rc) >= 275.0) {
                    continue;
                }
                gobs.add(gob);
            }
        }
        return gobs;
    }

    private Gob checkFlotsam() {
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/terobjs/herbs/flotsam"))) {
                    this.seenGobs.add(gob.id);
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob getDock(){
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/terobjs/knarrdock"))) {
                    this.seenGobs.add(gob.id);
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob getSnekkja(){
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/terobjs/vehicle/snekkja"))) {
                    this.seenGobs.add(gob.id);
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob getKnarr() {
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/terobjs/vehicle/knarr"))) {
                    this.seenGobs.add(gob.id);
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob checkOrca(){
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/kritter/orca/orca"))) {
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob checkWalrus(){
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/kritter/walrus/walrus"))) {
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob checkWhale(){
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/kritter/spermwhale/spermwhale"))) {
                    return gob;
                }
            }
        }
        return null;
    }

    private Gob checkgrey(){
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.equals("gfx/kritter/greyseal/greyseal"))) {
                    return gob;
                }
            }
        }
        return null;
    }

    private String findPlayerDescr() {
        synchronized (this.gui.map.glob.oc) {
            for (final Gob gob : this.gui.map.glob.oc) {
                Composite c = null;
                try {
                    c = this.getgcomp(gob);
                }
                catch (Loading loading) {}
                if (c != null && !this.seenGobs.contains(gob.id)) {
                    final StringBuilder sb = new StringBuilder();
                    for (final Composited.ED item : c.comp.cequ) {
                        sb.append(((Resource)item.res.res.get()).basename() + "\n");
                    }
                    for (final Composited.MD item2 : c.comp.cmod) {
                        sb.append(((Resource)item2.mod.get()).basename() + "\n");
                    }
                    this.seenGobs.add(gob.id);
                    final String allEq = sb.toString();
                    if (allEq.contains("mannequin")) {
                        continue;
                    }
                    if (allEq.contains("male") || allEq.contains("female")) {
                        return sb.toString();
                    }
                    continue;
                }
            }
        }
        return null;
    }

    private Composite getgcomp(final Gob gob) {
        if (gob == null) {
            return null;
        }
        final Drawable d = (Drawable)gob.getattr((Class)Drawable.class);
        if (!(d instanceof Composite)) {
            return null;
        }
        final Composite gc = (Composite)d;
        if (gc.comp == null) {
            return null;
        }
        return gc;
    }

    private Coord getNextLoc() {
        final Coord pltc = new Coord(this.gui.map.player().rc.floor().x / 11, this.gui.map.player().rc.floor().y / 11);
        final Coord pc = this.gui.map.player().rc.floor();
        final double curAng = this.ang;
        final int angles = 20;
        while (true) {
            if (this.clockwiseDirection == 1) {
                if (this.ang > curAng + Math.PI*2) {
                    break;
                }
            }
            else if (this.ang < curAng - Math.PI*2) {
                break;
            }
            boolean foundground = false;
            for (int i = 0; i < 20; ++i) {
                final Coord2d addcoord = new Coord2d(-Math.cos(-this.ang) * i * this.searchRadius, Math.sin(-this.ang) * i * this.searchRadius);
                final Coord t = pc.add(addcoord.floor());
                if (this.checkTiles(t)) {
                    foundground = true;
                }
            }
            if (!foundground) {
                final Coord2d addcoord2 = new Coord2d(-Math.cos(-this.ang) * 20.0 * this.searchRadius, Math.sin(-this.ang) * 20.0 * this.searchRadius);
                ++this.successLocs;
                return pc.add(addcoord2.floor());
            }
            this.successLocs = 0;
            this.ang += this.clockwiseDirection * 2 * Math.PI / angles;
        }
        return null;
    }

    private boolean checkTiles(final Coord t) {
        for (int rad = 2, i = -rad; i <= rad; ++i) {
            for (int j = -rad; j <= rad; ++j) {
                if (!this.isWater(t.add(i * 11, j * 11))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isWater(Coord t) {
        Coord pltc = new Coord(t.x / 11, t.y / 11);
        try {
            int dt = this.mcache.gettile(pltc);
            Resource res = this.mcache.tilesetr(dt);
            if (res == null) {
                return false;
            }
            String name = res.name;
            return name.equals("gfx/tiles/odeep");
        }
        catch (Loading e) {
            return false;
        }
    }

    private Coord2d findRandomGroundTileShallow() {
        final Coord2d basecoord = this.gui.map.player().rc;
        final int radius = 440;
        for (int i = 0; i < 1000; ++i) {
            final Coord2d rancoord = new Coord2d((double)(this.random.nextInt(radius * 2) - radius), (double)(this.random.nextInt(radius * 2) - radius));
            if (!this.isShallow(basecoord.add(rancoord).floor())) {
                return basecoord.add(rancoord);
            }
        }
        return basecoord;
    }

    private Coord2d findRandomShallowWaterTile() {
        final Coord2d basecoord = this.gui.map.player().rc;
        final int radius = 330;
        for (int i = 0; i < 1000; ++i) {
            final Coord2d rancoord = new Coord2d((double)(this.random.nextInt(radius * 2) - radius), (double)(this.random.nextInt(radius * 2) - radius));
            if (this.isShallow(basecoord.add(rancoord).floor())) {
                return basecoord.add(rancoord);
            }
        }
        return basecoord;
    }

    private Coord getNextLocShallow() {
        final Coord pltc = new Coord(this.gui.map.player().rc.floor().x / 11, this.gui.map.player().rc.floor().y / 11);
        final Coord pc = this.gui.map.player().rc.floor();
        final double curAng = this.ang;
        final int angles = 20;
        while (true) {
            if (this.clockwiseDirection == 1) {
                if (this.ang > curAng + 6.283185307179586) {
                    break;
                }
            }
            else if (this.ang < curAng - 6.283185307179586) {
                break;
            }
            boolean foundground = false;
            for (int i = 0; i < 20; ++i) {
                final Coord2d addcoord = new Coord2d(-Math.cos(-this.ang) * i * this.searchRadius, Math.sin(-this.ang) * i * this.searchRadius);
                final Coord t = pc.add(addcoord.floor());
                if (this.checkTilesShallow(t)) {
                    foundground = true;
                }
            }
            if (!foundground) {
                final Coord2d addcoord2 = new Coord2d(-Math.cos(-this.ang) * 20.0 * this.searchRadius, Math.sin(-this.ang) * 20.0 * this.searchRadius);
                ++this.successLocs;
                return pc.add(addcoord2.floor());
            }
            this.successLocs = 0;
            this.ang += this.clockwiseDirection * 2 * Math.PI / angles;
        }
        return null;
    }

    private boolean checkTilesShallow(final Coord t) {
        for (int rad = 2, i = -rad; i <= rad; ++i) {
            for (int j = -rad; j <= rad; ++j) {
                if (!this.isShallow(t.add(i * 11, j * 11))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isShallow(Coord t) {
        Coord pltc = new Coord(t.x / 11, t.y / 11);
        try {
            int dt = this.mcache.gettile(pltc);
            Resource res = this.mcache.tilesetr(dt);
            if (res == null) {
                return false;
            }
            String name = res.name;
            return name.equals("gfx/tiles/odeep");
        }
        catch (Loading e) {
            return false;
        }
    }
    public long gobtoId(Gob gob){
        return gob.id;
    }

    public void discordSendScreen(String file){
        File scrn = new File("res/saved.png");
        if (scrn.exists()){
            scrn.delete();
        }
        Screenshooter.take(gui);
        scrn = new File("res/saved.png");
        if (!scrn.exists()){
            while (!scrn.exists()){
                scrn = new File("res/saved.png");
                if (scrn.exists()) break;
            }
        }
        if (scrn.exists()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getDiscord().sendFile(file);
        }
    }
    @Override
    public void wdgmsg(Widget sender, String msg, Object... args) {
        if((sender == this) && (Objects.equals(msg, "close"))) {
            stop = true;
            stop();
            reqdestroy();
            gui.OrcaFinder = null;
        } else {
            super.wdgmsg(sender, msg, args);
        }
    }

    public void stop() {
        if (ui != null || gui != null) {
            ui.gui.map.wdgmsg("click", Coord.z, ui.gui.map.player().rc.floor(posres), 1, 0);
        }
        if (gui.map.pfthread != null) {
            gui.map.pfthread.interrupt();
        }
        if (gui.OrcaFinder != null) {
            gui.OrcaFinderThread.interrupt();
            gui.OrcaFinderThread = null;
        }
        this.destroy();
    }

    @Override
    public void close() {
        this.stop = true;
        this.stop();
        this.reqdestroy();
        gui.OrcaFinderThread.interrupt();
    }
}
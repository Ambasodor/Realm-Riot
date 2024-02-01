package haven.botengine;

import haven.Button;
import haven.Composite;
import haven.Label;
import haven.Window;
import haven.*;
import haven.botengine.entities.DoorData;
import haven.botengine.pathfinder.NoPathfinder;
import haven.botengine.pathfinder.Pathfinder;
import haven.render.Homo3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static haven.MCache.tilesz;
import static haven.OCache.posres;
import static haven.VMeter.Fuel_Color;
import static haven.VMeter.Water_Color;
import static haven.botengine.config.BotConfig.isWindowless;

@SuppressWarnings("unused")
public class BotPlayer {

    private static final Logger log = LoggerFactory.getLogger(BotPlayer.class);
    private final BotEnvironment environment;
    private final Pathfinder pathfinder;
    public Gob gob;
    public BotPlayer(BotEnvironment environment) {
        this.environment = environment;
        pathfinder = new NoPathfinder(environment);
    }

    // --------------------------------------------------
    // Chat
    // --------------------------------------------------
    public void areaSay(String message) {
        sayToChat(message, areaChat());
    }

    public void realmSay(String message, String name) {
        sayToChat(message, realmChat(name));
    }

    public void villageSay(String message) {
        sayToChat(message, villageChat());
    }

    public void discordSay(String message) {
        environment.getDiscord().sendMessage(message);
    }
    public void partySay(String message) {
        sayToChat(message, partyChat());
    }
    public String getlastmsgfromPartyChat(){
        String lst = ChatUI.lastmsage;
        lastmsgnull();
        return lst;
    }
 public List<ISBox> getisboxes(Window window){
     if (window != null) {
         List<ISBox> isboxes = new ArrayList<>();
         for (ISBox isbox : window.children(ISBox.class)) {
             if (isbox.visible) {
                 isboxes.add(isbox);
             }
         }
         return isboxes;
     }
     return null;
 }
 public String getNumberFromISBox(ISBox isBox, int i){
        if (isBox != null) {
            String segments[] = isBox.label.text.split("/");
            String isbox = segments[i];
            return isbox;
        }
        return null;
 }
    public void lastmsgnull(){
        ChatUI.lastmsage = "";
        ChatUI.areach = "";
    }
    public void setspeed(int sp){
    environment.getGui().speed.set(sp);
    }
    public void MarkOnMe(String markname){
                MapFile.Marker nm = new MapFile.PMarker(environment.getGui().mmap.curloc.seg.id, environment.getGui().mmap.curloc.tc, markname, BuddyWnd.gc[new Random().nextInt(BuddyWnd.gc.length)]);
                environment.getGui().mapfile.file.add(nm);
    }
    public void MarkOnMe(String markname, double F){
        StringBuilder stringBuilder = new StringBuilder(100);

        stringBuilder.append(markname + " " + F);
        String marker = stringBuilder.toString();
        MapFile.Marker nm = new MapFile.PMarker(environment.getGui().mmap.curloc.seg.id, environment.getGui().mmap.curloc.tc, marker, BuddyWnd.gc[new Random().nextInt(BuddyWnd.gc.length)]);
        environment.getGui().mapfile.file.add(nm);
    }

    public String getlastmsgfromArea(){
        String lst = ChatUI.areach;
        lastmsgnull();
        return lst;
    }

    public void discordSendFile(String message, String filepath){
        environment.getDiscord().sendFile(message, filepath);
    }
    public void discordSendScreen(String file){
        if (!isWindowless()) {
            File scrn = new File("res/saved.png");
            if (scrn.exists()) {
                scrn.delete();
            }
            Screenshooter.take(environment.getGui());
            scrn = new File("res/saved.png");
            if (!scrn.exists()) {
                while (!scrn.exists()) {
                    scrn = new File("res/saved.png");
                    if (scrn.exists()) break;
                }
            }
            if (scrn.exists()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                environment.getDiscord().sendFile(file);
            }
        }
    }
    public void discordSendScreen(String message, String file){
        if (!isWindowless()) {
            File scrn = new File("res/saved.png");
            if (scrn.exists()) {
                scrn.delete();
            }
            Screenshooter.take(environment.getGui());
            scrn = new File("res/saved.png");
            if (!scrn.exists()) {
                while (!scrn.exists()) {
                    scrn = new File("res/saved.png");
                    if (scrn.exists()) break;
                }
            }
            if (scrn.exists()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                environment.getDiscord().sendFile(message, file);
            }
        }
    }
    public void setMoji(){
        environment.getDiscord().setEmodji();
    }

    // --------------------------------------------------
    // Login / logout
    // --------------------------------------------------

    public void logout() {
        doUntilUIElementIsReady(() -> environment.getGui().act("lo"), LoginScreen.class);
    }

    public void unsafeLogout() {
        doUntilUIElementIsReady(() -> environment.getGui().ui.sess.close(), LoginScreen.class);
    }

    public void login(String username, String password, String character) throws InterruptedException {
        doUntilUIElementIsReady(() ->
                environment.getPanel().getUI().root.children(LoginScreen.class).forEach(loginScreen ->
                        loginScreen.wdgmsg("login", new AuthClient.NativeCred(username, password), false)
                ), Charlist.class
        );

        doUntilUIElementIsReady(() ->
                environment.getPanel().getUI().root.children(Charlist.class).forEach(charlist ->
                        charlist.wdgmsg("play", character)
                ), GameUI.class);

        Optional<GameUI> newGUI = environment.getPanel().findGameUI();
        while (!newGUI.isPresent()) {
            Thread.sleep(500);
            newGUI = environment.getPanel().findGameUI();
        }

        environment.setGui(newGUI.get());
    }
    public void isLoggined(){
        Optional<GameUI> newGUI = environment.getPanel().findGameUI();
        if (newGUI.isEmpty() || newGUI == null){
            discordSay("Im not ingame");
        }
        if (!newGUI.isEmpty() || newGUI != null){
            discordSay("Im ingame");
        }
    }
    // --------------------------------------------------
    // Actions
    // --------------------------------------------------
    public String getServerTime() {
        return environment.getGui().ui.sess.glob.mservertime;
    }

    public long findCountOf(String gob) {
        return retryOnConcurrentModificationException(() ->
                StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .map(Gob::getres)
                        .filter(res -> res != null && res.name.contains(gob))
                        .count()
        );
    }

    public List<String> DescribePerson(Gob gob){
        if (gob != null && gob.getres() != null && gob.getres().name.equals("gfx/borka/body")){
            List<String> items = new ArrayList<>();

            gob.getattr(Composite.class).comp.equ.forEach(equ ->
                    items.add(equ.desc.res.res.get().name.replace("gfx/terobjs/items/", "")));
            return items;
        }
        return List.of();
    }
    public List<Gob> FindAnotherPlayers(){
        List gobs = new ArrayList<>();
        synchronized (environment.getGui().map.getGlob().oc) {
            for (Gob gob : environment.getGui().map.getGlob().oc) {
                Resource resource = gob.getres();
                if (resource != null && !gob.isplayer() && gob.getres().name.equals("gfx/borka/body") && environment.getGui().map.player() != null && gob != environment.getGui().map.player()) {
                    gobs.add(gob);
                } else if (resource != null && !gob.isplayer() && gob.getres().name.equals("gfx/borka/body") && environment.getGui().map.player() == null){
                    gobs.add(gob);
                }
            }
            if (!gobs.isEmpty()) {
                return gobs;
            }
        }
            return List.of();
    }

public boolean menu (){
        FlowerMenu menu = environment.getUi().root.getchild(FlowerMenu.class);
        if (menu == null){
        return false;
        }
        return true;
}
public boolean isPlayerDead(){
        boolean ko;
        boolean cancarry;
        ko = getGobPoses(environment.getGui().map.player(), "gfx/borka/knock");
        cancarry = getGobPoses(environment.getGui().map.player(), "gfx/borka/carry");
        if (ko && cancarry) {
            return true;
        }
        return false;
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
    public boolean pickClosest(String... items) {
        return retryOnConcurrentModificationException(() -> {
            Coord2d playerCoord = environment.getGui().map.player().rc;
            return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                    .filter(gob -> {
                        if (gob.getres() == null) {
                            return false;
                        } else {
                            return Stream.of(items).anyMatch(item -> gob.getres().name.contains(item));
                        }
                    })
                    .min((gob1, gob2) -> (int) (gob1.rc.dist(playerCoord) - gob2.rc.dist(playerCoord)))
                    .map(gob -> {
                        Coord3f sc = Homo3D.obj2view(gob.getrc(), environment.getGui().map.basic.state());
                        environment.getGui().map.wdgmsg("click", new Coord((int) sc.x, (int) sc.y), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1);

                        FlowerMenu menu;
                        do {
                            menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
                        } while (menu == null);
                        menu.setNextSelection("Pick");
                        pathfinder.waitUntilStops();
                        return true;
                    }).orElse(false);
        });
    }
/*
    public void useDoor(Gob gob) {
        Coord doorcoords = DoorData.from(gob).getDoorCoordinates();
        environment.getGui().map.wdgmsg("click", Coord.z, doorcoords, 3, 0, 0, (int) gob.id, doorcoords, 0, 16);
    }

 */

    public void doAct(String act) {
        environment.getGui().menu.wdgmsg("act", act);
    }
    public boolean Party(){
        return environment.getGui().partyperm;
    }
    /*
    public String waitForNewLabelTest(String labelwindow){
        Label label1 = null;
        label1 = environment.getGui().children(Label.class);
        label1.text.text;
       return joined;
    }

     */
    public void ActSupport(){
        environment.getGui().menu.wdgmsg("act", "bp","column");
    }
public void printallres(){
    log.info("Printing all visible resources on the screen");
    log.info("------------------------------");
    synchronized (environment.getGui().map.getGlob().oc) {
        for (Gob gob : environment.getGui().map.getGlob().oc) {
            Resource resource = gob.getres();
            if (resource != null && !gob.isplayer()) {
                log.info(resource.name);
            }
        }
    }
    log.info("------------------------------");
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

    public List<Gob> findAllTrees(){
        synchronized (environment.getGui().map.getGlob().oc) {
            Gob player = environment.getGui().map.player();

            if (player != null) {
                Coord2d playerCoord = player.rc;
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "gfx/terobjs/trees/") && !BotUtils.resourceNameContains(gob,"stump") && !BotUtils.resourceNameContains(gob,"oldstump") && !BotUtils.resourceNameContains(gob,"oldtrunk") && (GetSdt(gob) == 16 || GetSdt(gob) == 17 || GetSdt(gob) == 21537 || GetSdt(gob) == 32 || GetSdt(gob) == 21739 || GetSdt(gob) == 21793))
                        .sorted(Comparator.comparing(gob -> gob.rc.dist(playerCoord)))
                        .collect(Collectors.toList());
            } else {
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "gfx/terobjs/trees/") && !BotUtils.resourceNameContains(gob,"stump") && !BotUtils.resourceNameContains(gob,"oldstump") && !BotUtils.resourceNameContains(gob,"oldtrunk") && (GetSdt(gob) == 16 || GetSdt(gob) == 17))
                        .collect(Collectors.toList());
            }
        }
    }

    public List<Gob> findAllLogs(){
        synchronized (environment.getGui().map.getGlob().oc) {
            Gob player = environment.getGui().map.player();

            if (player != null) {
                Coord2d playerCoord = player.rc;
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "log") || BotUtils.resourceNameContains(gob,"oldtrunk"))
                        .sorted(Comparator.comparing(gob -> gob.rc.dist(playerCoord)))
                        .collect(Collectors.toList());
            } else {
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "log") || BotUtils.resourceNameContains(gob,"oldtrunk"))
                        .collect(Collectors.toList());
            }
        }
    }
    public List<Gob> findAllBushes(){
        synchronized (environment.getGui().map.getGlob().oc) {
            Gob player = environment.getGui().map.player();

            if (player != null) {
                Coord2d playerCoord = player.rc;
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "gfx/terobjs/bushes/"))
                        .sorted(Comparator.comparing(gob -> gob.rc.dist(playerCoord)))
                        .collect(Collectors.toList());
            } else {
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "gfx/terobjs/bushes/"))
                        .collect(Collectors.toList());
            }
        }
    }
    public List<Gob> findAllStumps(){
        synchronized (environment.getGui().map.getGlob().oc) {
            Gob player = environment.getGui().map.player();

            if (player != null) {
                Coord2d playerCoord = player.rc;
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "stump") || BotUtils.resourceNameContains(gob,"oldstump"))
                        .sorted(Comparator.comparing(gob -> gob.rc.dist(playerCoord)))
                        .collect(Collectors.toList());
            } else {
                return StreamSupport.stream(environment.getGui().map.getGlob().oc.spliterator(), false)
                        .filter(gob -> BotUtils.resourceNameContains(gob, "stump") || BotUtils.resourceNameContains(gob,"oldstump"))
                        .collect(Collectors.toList());
            }
        }
    }
    public String getGobResName(Gob gob){
        return gob.getres().name;
    }
    public long gobtoId(Gob gob){
        return gob.id;
    }
    public CompletableFuture<Boolean> actOnObject(Gob gob, String action) {
        return BotUtils.actOnObject(gob, environment, action)
                .completeOnTimeout(false, 5, TimeUnit.SECONDS);
    }

    public CompletableFuture<Window> waitForNewWindow(String title) {
        return BotUtils.waitForNewWindow(environment, title)
                .completeOnTimeout(null, 5, TimeUnit.SECONDS);
    }
    public List<WItem> getItems(String name) {
        return getItemsFromInventory(environment.getGui().maininv, name);
    }
    public Window waitForNewWindowTest(String title) {
        return BotUtils.waitForNewWindowTest(environment, title);

    }
    public Window getWindow(String title) {
    return BotUtils.getWindow(environment, title);
    }
    public List<CheckBox> getcheckboxes(Window window){
        if (window != null) {
            List<CheckBox> checkbox = new ArrayList<>();
            for (CheckBox checkBox : window.children(CheckBox.class)) {
                if (checkBox.visible) {
                    checkbox.add(checkBox);
                }
            }
            return checkbox;
        }
        return null;
    }

        public void windowActivate(Window window){
            window.wdgmsg("activate");
    }
    public void doActToWindow(Window window, String arg0, String arg1){
    window.wdgmsg(arg0,arg1);
    }
    public void SetActiveCheckbox(CheckBox checkBox, String arg0, String arg1){
        checkBox.wdgmsg(arg0,arg1);
        if (!checkBox.state.get()){
            while (!checkBox.state.get()){
                if (checkBox.state.get()) break;
            }
        }
    }
    public void swap(WItem item) throws InterruptedException {
        item.item.wdgmsg("transfer", Coord.z);
        Thread.sleep(200);
    }

    public CompletableFuture<Void> swapnek(WItem item) {

        while (true) {
            item.item.wdgmsg("transfer", Coord.z);
            try {
                log.trace("Waiting for transfer...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return null;
            }
            break;
        }

        return null;
    }

    public boolean petalExists() {
        FlowerMenu menu = environment.getUi().root.findchild(FlowerMenu.class);
        if (menu != null) {
            return true;
        }
        return false;
    }

    public String PartyCheck() {
        String label = null;
        while (label == null) {
            for (Label childlabel : environment.getGui().children(Label.class)) {
                if (childlabel.texts.contains("has invited")) {
                    label = childlabel.texts;
                }
            }

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                return label;
            }
        }
        return label;
    }
    public Coord getCenterScreenCoord() {
        Coord sc, sz;
        sz = environment.getGui().map.sz;
        sc = new Coord((int) Math.round(Math.random() * 200 + sz.x / 2f - 100),
                (int) Math.round(Math.random() * 200 + sz.y / 2f - 100));
        return sc;
    }
    public void makePile() {
        environment.getGui().map.wdgmsg("itemact", getCenterScreenCoord(), environment.getGui().map.player().rc.floor(posres), 0);
    }
    public void placeThing(int x, int y) {
        environment.getGui().map.wdgmsg("place", environment.getGui().map.player().rc.add(x, y).floor(posres), 0, 1, 0);
    }

    public void placeShiftAllThing(int x, int y) {
        environment.getGui().map.wdgmsg("place", environment.getGui().map.player().rc.add(x, y).floor(posres), 0, 1, 1);
    }
    public List<WItem> getEquipmentItems(Widget widget, String wItem){
        return widget.children(WItem.class).stream()
                .filter(ww -> wItem.equals(ww.item.getName()))
                .collect(Collectors.toList());
    }

    public List<WItem> GetItem(String name){
        Inventory inventory = environment.getGui().maininv;
        return inventory.children(WItem.class).stream()
                .filter(wItem -> wItem.item.getName() != null)
                .filter(wItem -> wItem.item.getName().contains(name))
                .collect(Collectors.toList());
    }
    public List<WItem> getItemsFromInventory(Inventory inventory, String name) {
        return inventory.children(WItem.class).stream()
                .filter(wItem -> wItem.item.getName() != null)
                .filter(wItem -> wItem.item.getName().contains(name))
                .collect(Collectors.toList());
    }
    //не работает
    public WItem GetHighestQualityOfItem(String widgetname, String name) {
        Widget window = waitForNewWindowTest(widgetname);
        List<WItem> item = null;
        List<Pair> list = new ArrayList<Pair>();
        while (item == null) {
            item = GetItemWindow(window, name);
            if (item != null) break;
        }
        double info = 0;
        for (int n = 0; n < item.size(); n++) {
            WItem items = item.get(n);
            info = items.item.quality().q;
            list.add(new Pair(items, info));
        }
        double maxQ = 0;
        WItem maxQItem = null;
        for (int b = 0; b < list.size(); b++){
            Pair a = list.get(b);
            double v = (double) a.b;
            if (maxQ == 0) {
                maxQ = v;
                if ((WItem) a.a != null) {
                    maxQItem = (WItem) a.a;
                }
            }
            if (maxQ < v){
                maxQ = v;
                if ((WItem) a.a != null) {
                maxQItem = (WItem) a.a;
                }
            }

        }
        System.out.println("Max quality founded: " + maxQ);
        return maxQItem;
    }

    private List<WItem> GetItemWindow(Widget widget, String name) {
        return widget.children(WItem.class).stream()
                .filter(wItem -> wItem.item.getName() != null)
                .filter(wItem -> wItem.item.getName().contains(name))
                .collect(Collectors.toList());
    }
    public void rightClickGobAndSelectOption(final Gob gob, String action) throws InterruptedException {
        environment.getGui().map.wdgmsg("click", new Object[] { Coord.z, gob.rc.floor(OCache.posres), 3, 0, 0, (int)gob.id, gob.rc.floor(OCache.posres), 0, -1 });
        Thread.sleep(3000);
        FlowerMenuClick(action);
    }
    public List<WItem> getItems(Widget widget) {
        return new ArrayList<>(widget.getchild(Inventory.class).children(WItem.class));
    }
    public Gob checkPlayer(){
        synchronized (environment.getGui().map.glob.oc) {
            for (Gob gob : environment.getGui().map.glob.oc) {
                Resource res = null;
                if (!gob.isplayer()) {
                    try {
                        res = gob.getres();
                    } catch (Loading loading) {
                    }
                    if (res != null && (res.name.equals("gfx/borka/body"))) {
                        return gob;
                    }
                }
            }
        }
        return null;
    }
    public Gob getMyself(){
        return environment.getGui().map.player();
    }
    public void mapClick(int x, int y, int btn, int mod) {
        environment.getGui().map.wdgmsg("click", getCenterScreenCoord(), new Coord2d(x, y).floor(posres), btn, mod);
    }
    public void mineClick(int x, int y, int btn, int mod){
        Coord pltc = new Coord((int) (environment.getGui().map.player().rc.x/11), (int) (environment.getGui().map.player().rc.y/11));
        environment.getGui().map.wdgmsg("sel", new Coord(pltc.x, pltc.y), new Coord(pltc.x, pltc.y), 0);
    }
    public void ClickGob(Gob gob) {
        Coord2d destination = new Coord2d(gob.rc.x, gob.rc.y);
        environment.getGui().map.wdgmsg("click", new Coord(600, 600), gob.rc.floor(posres), 1, 0, 0, (int) gob.id, gob.rc.floor(posres), 1, 0);
    }
    public void cancelAct() {
        environment.getGui().map.wdgmsg("click", getCenterScreenCoord(), new Coord2d(0, 0).floor(posres), 3, 0);
    }

    public ArrayList<String> getPoses(Gob gob) {
        ArrayList<String> ret = new ArrayList<>();
        Drawable d = gob.getattr(Drawable.class);

        if (d instanceof Composite) {
            Composite comp = (Composite) d;
            for (ResData rd : comp.oldposes) {
                try {
                    ret.add(rd.res.get().name);
                } catch (Loading l) {

                }
            }
        }
        return ret;
    }
    public boolean getGobPoses(Gob gob, String string) {
        Drawable d = gob.getattr(Drawable.class);

        if (d instanceof Composite) {
            Composite comp = (Composite) d;
            for (ResData rd : comp.oldposes) {
                try {
                    if (rd.res.get().name.contains(string)) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }


    public String GetAccountName(){
        return MainFrame.tent;
    }
    public void dropItemFromInventory(WItem item) {
        item.item.wdgmsg("drop", Coord.z);
    }
    public void dropitemcour(){
        environment.getGui().vhand.item.wdgmsg("drop",Coord.z);
    }
    public Set<Button> getWindowButtons(Window w) {
        return w.children(Button.class);
    }
    public ArrayList<String> getWindowLabels(Window w){
        Set<Label> windowLabels = w.children(Label.class);
        if (!windowLabels.isEmpty()) {
            ArrayList<String> text = new ArrayList<>();
            for (Label windowLabel : windowLabels) {
                text.add(windowLabel.texts);
            }
            return text;
        }
        return null;
    }
public void closewindow(Window w){
        if (w != null) {
            w.wdgmsg("close");
        }
}
    public List<Button> getWindowButtons(Window w, String buttonText) {
        return w.children(Button.class).stream()
                .filter(button -> buttonText.equals(button.text.text))
                .collect(Collectors.toList());
    }

    // Click some object with button and a modifier
    // Button 1 = Left click and 3 = right click
    // Modifier 1 - shift; 2 - ctrl; 4 - alt;
    public void Click(Gob gob,int b, int m) {
        if (gob != null) {
            environment.getGui().map.wdgmsg(
                    "click", new Coord(600, 600), gob.rc.floor(posres), b, m, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
            );
            waitUntilStops();
        }
        if (gob == null) {
            while (true) {
                try {
                    Thread.sleep(500);
                    environment.getGui().map.wdgmsg(
                            "click", new Coord(600, 600), gob.rc.floor(posres), 3, 1, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
                    );
                    waitUntilStops();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public double getHourglass() {
        return environment.getGui().prog.prog;
    }
    public void playAlarmsound(){
        Defer.later(() -> {
            Audio.play(Resource.local().loadwait("custom/orcaAndWhale"));
            return (null);
        });
    }
    public void sysMsg(UI ui, String str, Color col) {
        if (environment.getGui() != null)
            environment.getGui().msg(str, col);
    }
    public void SystemMessage(String string){
        sysMsg(environment.getUi(),string, Color.pink);
    }
    public void ButtonMenuClick(String window, String buttonText, int b) {
        Window w = waitForNewWindowTest(window);
        Set<Button> children = w.children(Button.class);

        List<Button> buttons = getWindowButtons(w, buttonText);
        if (buttons == null) {
            log.info("No buttons appeared! Check again.");
            buttons = getWindowButtons(w, buttonText);
        }
        if (buttons != null) {
            buttons.get(b).click();
        }
    }
    public Set<String> GetEnemyKinsNearby() {
        Gob player = null;
        Set<String> names = new HashSet<>();

        synchronized (environment.getGui().map.glob.oc) {
            for (Gob gob : environment.getGui().map.glob.oc) {
                try {
                    Resource res = gob.getres();
                    KinInfo info = gob.getattr(KinInfo.class);
                    if (res != null && res.name.startsWith("gfx/borka/body") && info != null && info.group == 2) {
                        //log.info("kininfo {}", info.name);
                        names.add(info.name);
                        if (player == null)
                            player = gob;
                    }
                } catch (Loading l) {
                }
            }
        }
        return names;
    }
    public long GobIdGet(Gob gob){
        return gob.id;
    }
    public List<Gob> getGobbyFight(){
        if (environment.getGui().fv.current != null) {
            if (environment.getGui().fv.current.gobid != 0) {
                ArrayList<Gob> ret = new ArrayList<>();
                long gobid = environment.getGui().fv.current.gobid;
                int gobidnonm = environment.getGui().fv.nonmain.size();
                synchronized (environment.getGui().map.glob.oc) {
                    for (final Gob gob : environment.getGui().map.glob.oc) {
                        Resource res = null;
                        try {
                            res = gob.getres();
                        } catch (Loading loading) {
                        }
                        if (res != null && (gob.id == gobid)) {
                            ret.add(gob);
                        }
                        if (gobidnonm != 0) {
                            for (int d = 0; d < gobidnonm; d++) {
                                if (res != null && (gob.id == environment.getGui().fv.nonmain.get(d).gobid)) {
                                    ret.add(gob);
                                }
                            }
                        }
                    }
                }
                return ret;
            }
        }
        return List.of();
    }
    public List<String> GetGearByFight(Gob gob) {
        List<Gob> gobs = getGobbyFight();
        List<String> gear;
        if (!gobs.isEmpty()) {
                gear = (DescribePerson(gobs.get(0)));
            return gear;
        }
            return List.of();
    }
    public void closeGameWindow(){
        MainFrame.mt.interrupt();
    }
    public String GetAnimalByFight(Gob gob){
        return gob.getres().name;
    }
    public Gob GetGobByID(long id){
        synchronized (environment.getGui().map.glob.oc) {
            for (final Gob gob : environment.getGui().map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (gob.id == id)) {
                    return gob;
                }
            }
        }
        return null;
    }

    public void FlowerMenuClick(String action) {
        FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
        if (menu == null) {
            while (true) {
                menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
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
                menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
                try {
                    menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
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

    public FlowerMenu.Petal getflowermenu(String option) {
        FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
        if (menu != null) {
            for (FlowerMenu.Petal flowerMenu : menu.opts) {
                if (flowerMenu != null && flowerMenu.name.contains(option)) {
                    return flowerMenu;
                }
            }
        }
        return null;
    }



    public FlowerMenu getflower() throws InterruptedException {
        FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
        if (menu != null) {
            return menu;
        }
        return null;
    }
    public void ClickPetal(FlowerMenu.Petal petal){
        if (petal != null){
            FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
            menu.choose(petal);
        }
    }
    public void ClickFlower(String option) throws InterruptedException {
        FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
        if (menu != null) {
            FlowerMenu.Petal petal = getpetal(menu, option);
            if (petal != null) {
                menu.choose(petal);
            }
            Thread.sleep(1000);
        }
    }

    public FlowerMenu.Petal getpetal(FlowerMenu menu, String option) {
        for (FlowerMenu.Petal petal : menu.opts) {
            if (petal.name.equals(option)) {
                return petal;
            }
        }
        return null;
    }

    public Gob isGob(String item){
        synchronized (environment.getGui().map.glob.oc) {
            for (final Gob gob : environment.getGui().map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.contains(item))) {
                    return gob;
                }
            }
        }
        return null;
    }

    public void PickupGob(Gob gob){
        if (gob != null) {
            environment.getGui().map.wdgmsg(
                    "click", new Coord(600, 600), gob.rc.floor(posres), 1, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
            );
        }
    }
public void closeFmenu() throws InterruptedException {
    FlowerMenu menu = getflower();
    menu.wdgmsg("cl", -1);
}
    public Gob CheckGobByFlowerMenu(Gob gob, String option) {
            try {
                if (gob != null) {
                    environment.getGui().map.wdgmsg(
                            "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
                    );
                    Thread.sleep(500);
                    FlowerMenu menu = getflower();
                    if (menu != null) {
                        FlowerMenu.Petal petal = getpetal(menu, option);
                        menu.wdgmsg("cl", -1);
                        Thread.sleep(200);
                        if (petal != null)
                            return gob;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return null;
    }

    public boolean isOccupied(){
        Boolean occ = environment.getGui().occupied;
        if (occ == true){
            environment.getGui().occupied = false;
        }
        return occ;
    }
    public boolean isCollected (){
        Boolean occ = environment.getGui().collected;
        environment.getGui().collected = false;
        return occ;
    }

    public void unplaceThing() {
        environment.getGui().map.wdgmsg("place", environment.getGui().map.player().rc.floor(posres), 0, 3, 0);
    }

    public void RightClickGob(Gob gob) {
        if (gob != null) {
            environment.getGui().map.wdgmsg(
                    "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
            );
            waitUntilStops();
        }
        if (gob == null) {
            while (true) {
                try {
                    Thread.sleep(500);
                    environment.getGui().map.wdgmsg(
                            "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
                    );
                    waitUntilStops();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
public void UseDoor(Gob gob){
        Coord doorcoords = DoorData.from(gob).getDoorCoordinates();
        environment.getGui().map.wdgmsg("click", Coord.z, doorcoords, 3, 0, 0, (int) gob.id, doorcoords, 0, 16);
    }
    /*
    public void RightClickCoords(Coord2d coord2d) {
        Coord2d destination = new Coord2d(coord2d.x, coord2d.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 3,0,1, gob.id, destination.floor(posres),spit.id, -1);
    }

     */
    public int getOverlay(Gob gob, String resourcename){
        Collection<Gob.Overlay> ole = gob.ols;
        for (Gob.Overlay ol : ole) {
            if (ol != null) {
                if (ol.res.toString().contains(resourcename)){
                    return ol.id;
                }
            }
        }
        return 0;
    }
    public boolean isOverlay(Gob gob, String resourcename){
        Collection<Gob.Overlay> ole = gob.ols;
        for (Gob.Overlay ol : ole) {
            if (ol != null) {
                if (ol.res.toString().contains(resourcename)){
                    return true;
                } else if (!ol.res.toString().contains(resourcename)){
                    return false;
                }
            }
        }
        return false;
    }
    public Map<Class<? extends GAttrib>, GAttrib> getAttrib(Gob gob) {
        return gob.attr;
    }
    public void ClickObjectUnderGob(Gob gob,int a){
        environment.getGui().map.wdgmsg(
                "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 1, (int) gob.id, gob.rc.floor(posres), a, -1
        );
    }
    public void RightClickGobNoWait(Gob gob) {
        if (gob != null) {
            environment.getGui().map.wdgmsg(
                    "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
            );
        }
        if (gob == null) {
            while (true) {
                try {
                    Thread.sleep(500);
                    environment.getGui().map.wdgmsg(
                            "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
                    );
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void clickuderme(){
        environment.getGui().map.wdgmsg("click",Coord.z, environment.getGui().map.player().rc.floor(OCache.posres), 1, 0);
    }
public void buildrotate(int x, int y, int milestoneRot) {
    Coord2d ccc = environment.getGui().map.player().rc;
    ccc = ccc.add(x * 11, y * 11);
    Coord2d center = ccc.div(11).floord().mul(11).add(5.5,5.5);
    Coord2d buildPos = new Coord2d((double)center.x, (double)center.y);
    environment.getGui().map.wdgmsg("place", buildPos.floor(OCache.posres), milestoneRot, 1, 0);
}
    public Coord getCoordUnderme(){
        return environment.getGui().map.player().rc.floor(OCache.posres);
    }
    public void ClickCoord(Coord coord){
        Coord dcoord = new Coord(coord.x, coord.y);
        environment.getGui().map.wdgmsg("click", coord.z, dcoord, 1, 0);
    }
    public void playALARM(String args) {
        Audio.play(Loading.waitfor(Resource.remote().load(args)));
    }


    public void RightClickGobnomove(Gob gob) {
        if (gob != null) {
            if (gob != null) {
                environment.getGui().map.wdgmsg(
                        "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
                );
            }
            if (gob == null) {
                while (true) {
                    try {
                        Thread.sleep(500);
                        environment.getGui().map.wdgmsg(
                                "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
                        );
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void useOn(Gob gob) {
        environment.getGui().map.wdgmsg("itemact", Coord2d.z, gob.rc.floor(posres), 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1);
    }
    public void useOnEverything(Gob gob) {
        environment.getGui().map.wdgmsg("itemact", Coord2d.z, gob.rc.floor(posres), 1, 1, (int) gob.id, gob.rc.floor(posres), 0, -1);
    }
    public void cancel() {
        environment.getGui().map.wdgmsg("click", Coord.z, Coord2d.z.floor(posres), 3, 0);
    }

    public int GetSdt(Gob gob){
        if (gob != null) {
            int gobek = gob.sdt();
            return gobek;
        }
        return -10;
    }

    public double getWindowMeters(Window window){
        if (window != null) {
            return window.getchild(LayerMeter.class).meters.get(0).a;
        }
        return -1;
    }

    public Coord2d getPlayerCoords() {
        return environment.getGui().map.player().rc;
    }
    public Coord2d getGobCoords(Gob gob) {
        return gob.rc;
    }

    public Coord2d getRoundedPlayerCoords() {
        return Coord2d.of(environment.getGui().map.player().rc.round());
    }

    public void move(int x, int y) {
        pathfinder.move(x, y);
    }

    public void moveTo(Coord2d coordinate) {
        pathfinder.moveTo(coordinate);
    }

    public void moveTo(Gob gob) {
        pathfinder.moveTo(gob);
    }

    public void moveTo(int x, int y) {
        pathfinder.move(x, y);
    }
    public void moveToMussel(Gob gob) {
        pathfinder.moveToMussel(gob);
    }
    public void moveToMussel(Coord2d coordinate) {
        pathfinder.moveToMussel(coordinate);
    }
    public void moveToClay(Coord2d coordinate) {pathfinder.moveToClay(coordinate);}

    private void moveWithoutUnblockinggeb(Gob gob) {
        Coord2d playerCoord = environment.getGui().map.player().rc;
        environment.getGui().map.wdgmsg("click", (int) gob.rc.x, (int) gob.rc.y, 1, 0);
    }

    public void LeftClickGob(Gob gob){
        Coord2d destination = new Coord2d(gob.rc.x, gob.rc.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 0);
    }
    public void LeftClickCoordCTRL(Coord2d coordinate){
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 2);
    }
    public void LeftClickCoord(Coord2d coordinate){
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.x, destination.y, 1, 0);
    }
    public void RightClickCoord(Coord2d coordinate){
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 3, 0);
    }
    public void carrygob(Gob gob){
        Coord2d destination = new Coord2d(gob.rc.x, gob.rc.y);
        environment.getGui().map.wdgmsg("click", Coord.z, gob.rc.floor(posres), 1, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1);

    }
    public void rightclick(Coord2d coordinate){
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 3, 0, 0, destination.floor(posres), 0, -1);
    }
    public void rightclickgob(Gob gob){
        Coord2d destination = new Coord2d(gob.rc.x, gob.rc.y);
        environment.getGui().map.wdgmsg("click", Coord.z, gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1);
    }
    public void act(String... args) {
        environment.getGui().menu.wdgmsg("act", (Object[]) args);
    }
    public Integer getTimeInspect(){
        if (!environment.getGui().gettime.isEmpty()) {
            return environment.getGui().gettime.get(environment.getGui().gettime.size() - 1);
        }
        return -10;
    }
    public String getLastTimeInspectString(){
        if (!environment.getGui().gettimestring.isEmpty()) {
            String[] word = environment.getGui().gettimestring.get(environment.getGui().gettimestring.size() - 1).split(" ");
            if (word[4] != null) {
                return word[4];
            }
        }
        return null;
    }

    public void moveToNoPF(Coord2d coordinate) {
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        environment.getGui().map.wdgmsg("click", Coord.z, destination.floor(posres), 1, 0);
    }

    public void sidestep(double distance) {
        pathfinder.sidestep(distance);
    }

    public Integer getGateStatus(){
        synchronized (environment.getGui().map.glob.oc) {
            for (final Gob gob : environment.getGui().map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.contains("polebiggate"))) {
                    return gob.sdt();
                }
            }
        }
        return null;
    }

    public Integer getsmallGateStatus(){
        synchronized (environment.getGui().map.glob.oc) {
            for (final Gob gob : environment.getGui().map.glob.oc) {
                Resource res = null;
                try {
                    res = gob.getres();
                }
                catch (Loading loading) {}
                if (res != null && (res.name.contains("polegate"))) {
                    return gob.sdt();
                }
            }
        }
        return null;
    }

    public Boolean gateis(Gob gob, int istrue){
                    if (gob.sdt() == istrue){
                        return true;
                    }
                    if (gob.sdt() != istrue){
                        return false;
                    }
        return null;
    }

    public void waitUntilStops() {
        pathfinder.waitUntilStops();
    }

        public double distanceTo(Gob gob) {
        if (gob != null) {
            return environment.getGui().map.player().rc.dist(gob.rc);
        }
        return -1;
    }
    public double distanceBetween(Gob g1, Gob g2) {
        if (g1 != null) {
            return g1.rc.dist(g2.rc);
        }
        return -1;
    }

    public void randommove(int multiplier){
        moveWithoutUnblocking(
                (int) Math.round(Math.random() * multiplier - multiplier / 2.0),
                (int) Math.round(Math.random() * multiplier - multiplier / 2.0)
        );
    }
public MapMesh getTilePool(int x, int y){
    try {
        Coord loc = new Coord(x, y);
        int t = environment.getGui().map.glob.map.gettile(loc.div(11));
        MapMesh res = environment.getGui().map.glob.map.getcut(loc);
        if (res != null)
            return res;
        else
            return null;
    } catch (Loading l) {

    }
    return null;
}
    public String tileResnameAt( int x, int y) {
        try {
            Coord loc = new Coord(x, y);
            int t = environment.getGui().map.glob.map.gettile(loc.div(11));
            Resource res = environment.getGui().map.glob.map.tilesetr(t);
            if (res != null)
                return res.name;
            else
                return null;
        } catch (Loading l) {

        }
        return null;
    }
    public double SdistanceBetweenCoords(Coord2d coordinate) {
        Coord2d destination = new Coord2d(coordinate.x, coordinate.y);
        double distanceLeft;
        if (environment.getGui().map.player() != null) {
            distanceLeft = environment.getGui().map.player().rc.dist(destination);
            return distanceLeft;
        }
        return -1;
    }


    public double distanceBetweenCoords(int x, int y) {
        Coord2d destination = new Coord2d(x, y);
        double distanceLeft;
        if (environment.getGui().map.player() != null) {
            distanceLeft = environment.getGui().map.player().rc.dist(destination);
            return distanceLeft;
        }
        return -1;
    }

    private void moveWithoutUnblocking(int x, int y) {
        Coord2d playerCoord = environment.getGui().map.player().rc;
        environment.getGui().map.wdgmsg("click", Coord.z, new Coord2d(playerCoord.x + x, playerCoord.y + y).floor(posres), 1, 0);
    }

    private void moveToWithoutUnblocking(int x, int y) {
        environment.getGui().map.wdgmsg("click", Coord.z, new Coord2d(x, y).floor(posres), 1, 0);
    }

    public void activateItem(WItem item) {
        item.item.wdgmsg("iact", Coord.z, 3);
    }

    // --------------------------------------------------
    // Player status
    // --------------------------------------------------


    public int getStaminaMeter() {
        IMeter stamina = environment.getGui().getmeter("stam");
        if (stamina != null) {
            String tt = stamina.getmeter();
            tt = tt.substring(tt.indexOf(": ") + 2);
            tt = tt.replace("%", "");
            int ttNumber = Integer.parseInt(tt);
            return ttNumber;
        }
        return 0;
    }

    public String getHpMeter() {
        IMeter hp = environment.getGui().getmeter("hp");
        if (hp != null) {
            String tt = hp.getmeter();
            tt = tt.substring(tt.indexOf(": ") + 2);
            return tt;
        }
        return "";
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

    public int getEnergyMeter() {
        IMeter hp = environment.getGui().getmeter("nrj");
        if (hp != null) {
            String tt = hp.getmeter();
            tt = tt.substring(tt.indexOf(": ") + 2);
            if (tt.contains("(Healing)")) {
                tt = tt.substring(0, tt.length() - 10);
            }
            if (tt.contains("(Starving)")) {
                tt = tt.substring(0, tt.length() - 11);
            }
            tt = tt.substring(0, tt.length() - 1);
            int ttNumber = Integer.parseInt(tt);
            return ttNumber;
        }
        return 0;
    }

    // --------------------------------------------------
    // Chat helpers
    // --------------------------------------------------

    private Predicate<ChatUI.MultiChat> areaChat() {
        return chat -> "Area Chat".equals(chat.name());
    }

    private Predicate<ChatUI.MultiChat> partyChat() {
        return chat -> "Party".equals(chat.name());
    }

    private Predicate<ChatUI.MultiChat> realmChat(String name) {
        return chat -> chat.name().contains(name);
        //return chat -> chatname.equals(chat.name());
    }

    private Predicate<ChatUI.MultiChat> villageChat() {
        return areaChat().negate();
        //return areaChat().negate().and(realmChat().negate());
    }

    private void sayToChat(String message, Predicate<ChatUI.MultiChat> condition) {
        environment.getGui().chat.children(ChatUI.MultiChat.class)
                .stream()
                .filter(condition)
                .findAny()
                .ifPresent(chat -> chat.send(message));
    }

    // --------------------------------------------------
    // UI Helpers
    // --------------------------------------------------

    private <T extends Widget> void doUntilUIElementIsReady(Runnable action, Class<T> widgetClass) {
        int i = 0;
        while (environment.getPanel().getUI().getRwidgets().keySet()
                .stream()
                .noneMatch(widget -> widget.getClass().isAssignableFrom(widgetClass) && widget.visible && widget.hasfocus)
        ) {
            try {
                if (i % 10 == 0) {
                    action.run();
                }
                i += 1;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
                    Thread.sleep(50);
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
                        Thread.sleep(50);
                    } catch (Exception e) {
                    }
                } while (walking);
                log.trace("Character stopped.");
            }
        }
    }

    // --------------------------------------------------
    // Helper functions
    // --------------------------------------------------

    private <T> T retryOnConcurrentModificationException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (ConcurrentModificationException ex) {
            //log.debug("Retrying because found an exception: {}", ex.getMessage());
            return retryOnConcurrentModificationException(supplier);
        }
    }
    // --------------------------------------------------
// Vmeter functions
// --------------------------------------------------
    public double getFuel(Window window) {
        if (window != null) {
            for (VMeter vm : window.getchilds(VMeter.class)) {
                if (vm.color != null) {
                    if (vm.color.equals(Fuel_Color)) {
                        return vm.amount * 100;
                    }
                }
            }
        }
        return -1;
    }
    void method() {
        try {
            //some shit
        } catch (Loading l) {
            l.waitfor(() -> method(), w -> {});
        }
    }
    public Double getItemMeters(WItem item){
            if (item != null) {
                Double itema = item.itemmeter.get();
                if (itema == null) {
                    while (itema == null) {
                        itema = item.itemmeter.get();
                        if (itema != null || item == null);
                    }
                }
                double itemcalc = Math.floor(itema * 10000) / 100;
                return itemcalc;
            }
        return -1.0;
    }

    public double getWater(Window window){
        if (window != null) {
            for (VMeter vm : window.getchilds(VMeter.class)) {
                if (vm.color.equals(Water_Color)) {
                    return vm.amount * 100;
                }
            }
        }
        return -1;
    }

    ////movement
    public void clickcenter(Coord2d coord2d){
        Coord2d center = coord2d.div(11).floord().mul(11).add(5.5,5.5);
        environment.getGui().map.wdgmsg("click", Coord.z, center.floor(posres), 1, 0);
    }
    public void placecenter(Coord2d coord2d){
        Coord2d center = coord2d.div(11).floord().mul(11).add(5.5,5.5);
        environment.getGui().map.wdgmsg("place", center.floor(posres), 0, 1, 0);
    }
    public Coord2d getMytile(){
        return environment.getGui().map.player().rc.div(11).floord().mul(11);
    }
    public Coord2d CentergetMytile(){
        return getMytile().add(5.5,5.5);
    }
    public Coord getMytileCoord(){
        Coord d = environment.getGui().map.player().rc.floor();
        return d;
    }
    public void mine(Coord2d coord2d){
        Coord2d center = coord2d.div(11).floord().mul(11);
        Coord pltc = new Coord((int) (center.x / 11), (int) (center.y / 11));
        environment.getGui().map.wdgmsg("sel", new Coord(pltc.x, pltc.y), new Coord(pltc.x, pltc.y), 0);
    }
    public Coord makeCoord(Coord2d c){
        Coord s = new Coord((int) c.x,(int) c.y);
        return s;
    }
    public Pair<Coord,Coord> makepair(Coord c, Coord c1){
        Pair<Coord,Coord> lul = new Pair<>(c,c1);
        return lul;
    }

}


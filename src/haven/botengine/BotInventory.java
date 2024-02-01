package haven.botengine;

import haven.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class BotInventory {

    private static final Logger log = LoggerFactory.getLogger(BotInventory.class);
    private final BotEnvironment environment;

    public BotInventory(BotEnvironment environment) {
        this.environment = environment;
    }

    public boolean hasFreeSpace() {
        return getFreeSpace() > 0;
    }

    public int getFreeSpace() {
        return calculateFreeSpace(environment.getGui().maininv);
    }

    public int getFreeSpace(Widget widget) {
        return Optional.ofNullable(widget.getchild(Inventory.class))
                .map(this::calculateFreeSpace)
                .orElse(0);
    }

    public Optional<WItem> getHoldingItem() {
        return Optional.ofNullable(environment.getGui().vhand);
    }

    public Optional<Boolean> CoursorIsLeashed(){
        return Optional.ofNullable(environment.getGui().vhand.item.info().contains("leashed"));
    }

    public List<WItem> getItems(String name) {
        return getItemsFromInventory(environment.getGui().maininv, name);
    }
    public List<WItem> getItemsContain(String name) {
        return getItemsFromInventoryContains(environment.getGui().maininv, name);
    }
    public List<WItem> getItems(Widget widget, String name) {
        return Optional.ofNullable(widget.getchild(Inventory.class))
                .map(inventory -> getItemsFromInventory(inventory, name))
                .orElse(Collections.emptyList());
    }
    public List<WItem> getItemsContains(Widget widget, String name) {
        return Optional.ofNullable(widget.getchild(Inventory.class))
                .map(inventory -> getItemsFromInventoryContains(inventory, name))
                .orElse(Collections.emptyList());
    }

    public List<WItem> getItems(Widget widget) {
        return new ArrayList<>(widget.getchild(Inventory.class).children(WItem.class));
    }
    public void takeItem(WItem wItem) {
        if (isLeashed(wItem)) throw new RuntimeException("Tried to pick up a leashed item");
        wItem.item.wdgmsg("take", wItem.c);

        while (!getHoldingItem().isPresent()) {
            try {
                log.trace("Waiting for item...");
                Thread.sleep(500);
                wItem.item.wdgmsg("take", wItem.c);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    //не работает
    public double getitemquality(WItem witem){
        if (witem != null) {
            return Math.round(witem.item.quality().q);
        }
        return -1;
    }
    public Integer getquantityfromstack(WItem item){
        return item.item.contents.getchilds(GItem.class).size();
    }
    public Integer getAllitemsquantityfromstack(String name){
        List<WItem> items = getItemsFromInventoryContainsStack(environment.getGui().maininv, name);
        int total = 0;
        if (items != null && !items.isEmpty()){
            for (int a = 0; a != items.size(); a++) {
                total += items.get(a).item.contents.getchilds(WItem.class).size();
            }
                return total;
        }
        return 0;
    }
    public Integer getAllitemsquantitynotstack(String name){
        List<WItem> items = getItemsFromInventoryContainsNoStack(environment.getGui().maininv, name);
        int total = 0;
        if (items != null && !items.isEmpty()){
                total += items.size();
            return total;
        }
        return 0;
    }

    public ArrayList<Coord> getitemCoord(WItem item){
        ArrayList<Coord> coords = new ArrayList<>();
        coords.add(item.item.c);
        return coords;
    }
    public void putToInventory(Coord coord){
        Inventory inventory = environment.getGui().maininv;

        int freeSpace = calculateFreeSpace(inventory);
        inventory.wdgmsg("drop", new Coord(coord.x,coord.y));

        while (calculateFreeSpace(inventory) == freeSpace) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void putToInventory() {
        Inventory inventory = environment.getGui().maininv;

        int freeSpace = calculateFreeSpace(inventory);
        inventory.wdgmsg("drop", Coord.z);

        while (calculateFreeSpace(inventory) == freeSpace) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isLeashed(WItem wItem) {
        return wItem.item.info().stream().anyMatch(item -> "Leashed".equals(item.getClass().getSimpleName()));
    }

    public void unleash(WItem wItem) {
        if (!isLeashed(wItem)) throw new RuntimeException("Tried to unleash item that wasn't leashed");
        BotUtils.actOnItem(environment, wItem, "Unleash", Duration.ofSeconds(5));
    }

    public void shoo(WItem wItem) {
        if (!isLeashed(wItem)) throw new RuntimeException("Tried to shoo item that wasn't leashed");
        BotUtils.actOnItem(environment, wItem, "Shoo", Duration.ofSeconds(5));
    }

    public void eat(WItem wItem) {
        BotUtils.actOnItem(environment, wItem, "Eat", Duration.ofSeconds(5));
    }

    public int getItemCount(String name) {
        return getItems(name).size();
    }

    public int getItemCount(Widget widget, String name) {
        return getItems(widget, name).size();
    }

    public CompletableFuture<Void> transfer(WItem wItem) {
        int initialCount = getFreeSpace();

        while (getFreeSpace() == initialCount) {
            wItem.item.wdgmsg("transfer", Coord.z);

            try {
                log.trace("Waiting for transfer...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return null;
            }
        }

        return null;
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
    public CompletableFuture<Void> transferfromstockpile(ISBox isbox, int count){
        int o = 0;
        int s = 0;
        for (int a = 0; a < count; a++) {
            s = a;
            while (o == s) {
                isbox.wdgmsg("xfer2", -1, 1);
                try {
                    log.trace("Waiting for transfer...");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return null;
                }
                o++;
            }
        }
        return null;
    }
    public CompletableFuture<Void> transferNew(WItem wItem) {
        int initialCount = getFreeSpace();

        while (getFreeSpace() == initialCount) {
            wItem.item.wdgmsg("transfer", new Coord(wItem.item.sz.x, wItem.item.sz.y), -1);
            try {
                log.trace("Waiting for transfer...");
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return null;
            }
        }

        return null;
    }

    public CompletableFuture<Void> transferNewFast(WItem wItem) {
        int initialCount = getFreeSpace();

        while (getFreeSpace() == initialCount) {
            wItem.item.wdgmsg("transfer", new Coord(wItem.item.sz.x, wItem.item.sz.y), -1);
            try {
                log.trace("Waiting for transfer...");
                Thread.sleep(10);
            } catch (InterruptedException e) {
                return null;
            }
        }

        return null;
    }

    public double getItemMeter(WItem item){
        if (item != null) {
            if (getContents(item) != null) {
                return getContents(item).content.count;
            }
            if (getContents(item) == null) {
                return 0;
            }
        }
        return -1;
    }



    // --------------------------------------------------
    // Inventory helpers
    // --------------------------------------------------

    private int calculateFreeSpace(Inventory inventory) {
        int inventorySquareSize = Inventory.sqsz.x * Inventory.sqsz.y;
        int freeSpace = inventory.isz.x * inventory.isz.y;

        for (WItem item : inventory.children(WItem.class)) {
            freeSpace -= item.sz.x * item.sz.y / inventorySquareSize;
        }

        return freeSpace;
    }

    public List<WItem> getItemsFromInventory(Inventory inventory, String name) {
        return inventory.children(WItem.class).stream()
                .filter(wItem -> wItem.item.getName() != null)
                .filter(wItem -> wItem.item.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<WItem> getItemsFromInventoryContains(Inventory inventory, String name) {
        return inventory.children(WItem.class).stream()
                .filter(wItem -> wItem.item.getName() != null)
                .filter(wItem -> wItem.item.getName().contains(name))
                .collect(Collectors.toList());
    }
    public ArrayList<WItem> getItemsFromInventoryContainsStack(Inventory inventory, String name) {
        Set<WItem> wItem = inventory.children(WItem.class);
        if (!wItem.isEmpty()) {
            ArrayList<WItem> wms = new ArrayList<>();
            for (WItem w : wItem) {
                if (w.item.getName() != null) {
                    if (w.item.getName().contains(name)) {
                        if (w.item.contents != null && w.item.contents.getchilds(WItem.class).size() > 1 ||w.item.contents != null && w.item.contents.getchilds(WItem.class).size() > 0) {
                            wms.add(w);
                        }
                    }
                }
            }
            return wms;
        }
        return null;
    }
    public ArrayList<WItem> getItemsFromInventoryContainsNoStack(Inventory inventory, String name) {
        Set<WItem> wItem = inventory.children(WItem.class);
        if (!wItem.isEmpty()) {
            ArrayList<WItem> wms = new ArrayList<>();
            for (WItem w : wItem) {
                if (w.item.getName() != null) {
                    if (w.item.getName().contains(name)) {
                        if (w.item.contents == null || w.item.contentswnd == null) {
                            wms.add(w);
                        }
                    }
                }
            }
            return wms;
        }
        return null;
    }
    private ItemInfo.Contents getContents(WItem item) {
        if (item == null)
            return null;
        synchronized (item.item.ui) {
            try {
                for (ItemInfo info : item.item.info())
                    if (info instanceof ItemInfo.Contents)
                        return (ItemInfo.Contents) info;
            } catch (Loading ignored) {
            }
        }
        return null;
    }
}

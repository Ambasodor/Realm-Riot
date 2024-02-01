package haven.botengine;

import haven.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static haven.OCache.posres;

public final class BotUtils {

    private static final Logger log = LoggerFactory.getLogger(BotUtils.class);

    public static boolean resourceNameContains(Gob gob, String text) {
        try {
            Resource res = gob.getres();
            if (res != null && res.name.contains(text)) {
                return true;
            }
        } catch (Loading l) {
            // Ignored
        }

        return false;
    }

    public static CompletableFuture<Boolean> actOnObject(Gob gob, BotEnvironment environment, String action) {
        return CompletableFuture.supplyAsync(() -> {
            environment.getGui().map.wdgmsg(
                    "click", new Coord(600, 600), gob.rc.floor(posres), 3, 0, 0, (int) gob.id, gob.rc.floor(posres), 0, -1
            );

            FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
            while (menu == null) {
                menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return false;
                }
            }

            menu.choosestring(action);
            return true;
        });
    }

    public static void actOnItem(BotEnvironment environment, WItem wItem, String action, Duration timeout) {
        Temporal startTime = LocalTime.now();
        wItem.item.wdgmsg("iact", Coord.z, -1);

        FlowerMenu menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
        while (menu == null && Duration.between(LocalTime.now(), startTime).minus(timeout).isNegative()) {
            menu = environment.getGui().ui.root.getchild(FlowerMenu.class);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (menu == null) {
            log.warn("Tried to invoke {} on {} but it failed after {}", action, wItem.item.getName(), timeout);
        } else {
            menu.choosestring(action);
        }
    }

    public static CompletableFuture<Window> waitForNewWindow(BotEnvironment environment, String windowTitle) {
        return CompletableFuture.supplyAsync(() -> {
            // We want to look for a new window, so all existing ones need to be excluded
            Set<Window> existingWindows = environment.getGui().children(Window.class).stream()
                    .filter(window -> windowTitle.equals(window.cap))
                    .collect(Collectors.toSet());

            Window window = null;
           while (window == null) {
                for (Window childWindow : environment.getGui().children(Window.class)) {
                    if (windowTitle.equals(childWindow.cap) && !existingWindows.contains(childWindow)) {
                        window = childWindow;
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    return window;
                }
            }

            return window;
        });
    }
    public static Window waitForNewWindowTest(BotEnvironment environment, String windowTitle) {

        Window window = null;
        while (window == null) {
            for (Window childWindow : environment.getGui().children(Window.class)) {
                if (windowTitle.equals(childWindow.cap)) {
                    window = childWindow;
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return window;
            }
        }
        return window;
    }
    public static Window getWindow(BotEnvironment environment, String windowTitle){
        Window window = null;
        for (Window childWindow : environment.getGui().children(Window.class)) {
            if (windowTitle.equals(childWindow.cap)) {
                window = childWindow;
            }
        }
        if (window != null){
            return window;
        }
        return null;
    }
}

package haven.commands;

import haven.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintResources implements Console.Command {

    private static final Logger log = LoggerFactory.getLogger(PrintResources.class);
    private final GameUI gui;

    public PrintResources(JOGLPanel panel) {
        gui = panel.findGameUI().orElseThrow(() -> new RuntimeException("Scripts cannot be run without gui."));
    }

    @Override
    public void run(Console cons, String[] args) {
        log.info("Printing all visible resources on the screen");
        log.info("------------------------------");
        synchronized (gui.map.getGlob().oc) {
            for (Gob gob : gui.map.getGlob().oc) {
                Resource resource = gob.getres();
                if (resource != null && !gob.isplayer()) {
                    log.info(resource.name);
                }
            }
        }
        log.info("------------------------------");
    }

}

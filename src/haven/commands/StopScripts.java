package haven.commands;

import haven.Console;
import haven.GameUI;
import haven.JOGLPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class StopScripts implements Console.Command {

    private static final Logger log = LoggerFactory.getLogger(StopScripts.class);

    private final ExecutorService scriptExecutorService;
    private final GameUI gui;

    public StopScripts(ExecutorService scriptExecutorService, JOGLPanel panel) {
        this.scriptExecutorService = scriptExecutorService;
        gui = panel.findGameUI().orElseThrow(() -> new RuntimeException("Scripts cannot be run without gui."));
    }

    @Override
    public void run(Console cons, String[] args) {
        log.info("Shutting down all the running scripts.");
        scriptExecutorService.shutdown();
        try {
            boolean shutdown = scriptExecutorService.awaitTermination(5, TimeUnit.SECONDS);
            if (!shutdown) {
                log.error("Could not close all the running scripts.");
                gui.msg("Could not close all the running scripts.");
            } else {

                log.info("Scripts shut down.");
                gui.msg("Scripts shut down.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Shutting down scripts was interrupted.");
            gui.msg("Shutting down scripts was interrupted.");
        }
    }

}

package haven.commands;

import haven.Console;
import haven.GameUI;
import haven.JOGLPanel;
import haven.botengine.ScriptRunner;

import java.awt.*;
import java.time.Duration;

public class RunScript implements Console.Command {

    private final GameUI gui;
    private final ScriptRunner scriptRunner;

    public RunScript(JOGLPanel panel, ScriptRunner scriptRunner) {
        this.scriptRunner = scriptRunner;
        gui = panel.findGameUI().orElse(null);
    }

    @Override
    public void run(Console cons, String[] args) {
        if (args.length == 2) {
            String scriptName = args[1];
            runNow(scriptName);
        } else if (args.length == 4) {
            String scriptName = args[1];
            if ("every".equals(args[2])) {
                scheduleEvery(scriptName, Duration.parse("PT" + args[3]));
            } else if ("in".equals(args[2])) {
                scheduleIn(scriptName, Duration.parse("PT" + args[3]));
            }
        } else {
            gui.msg("Provide a script to run", Color.RED);
        }
    }

    // --------------------------------------------------
    // Runners
    // --------------------------------------------------

    private void runNow(String scriptName) {
        scriptRunner.schedule(scriptName);
    }

    private void scheduleEvery(String scriptName, Duration time) {
        scriptRunner.scheduleEvery(scriptName, time);
    }

    private void scheduleIn(String scriptName, Duration time) {
        scriptRunner.scheduleIn(scriptName, time);
    }

}

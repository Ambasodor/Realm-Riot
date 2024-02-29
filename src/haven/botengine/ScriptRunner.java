package haven.botengine;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import haven.GameUI;
import haven.JOGLPanel;
import haven.UI;
import haven.botengine.discord.Discord;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.*;

public class ScriptRunner {

    private static final Logger log = LoggerFactory.getLogger(ScriptRunner.class);

    private final ExecutorService scriptExecutorService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private final JOGLPanel panel;
    private final Discord discord;
    private UI ui;
    private GameUI gui;


    public final Queue<String> scriptQueue = new LinkedList<>();
    public final Map<String, ScheduledFuture<?>> scheduledTasks = new LinkedHashMap<>();
    private final Map<String, Object> globalVariables = new HashMap<>();
    public volatile String runningScript = null;


    public ScriptRunner(ExecutorService scriptExecutorService, JOGLPanel panel, Discord discord) {
        this.scriptExecutorService = scriptExecutorService;
        this.panel = panel;
        this.discord = discord;
    }

    public Queue<String> getScriptQueue() {
        return scriptQueue;
    }

    public Map<String, ScheduledFuture<?>> getScheduledTasks() {
        synchronized (scheduledTasks) {
            return scheduledTasks;
        }
    }

    public Optional<String> getRunningScript() {
        return Optional.ofNullable(runningScript);
    }
    public void schedule(String script) {
        gui = panel.findGameUI().orElse(null);
        File scriptFile = new File(String.format("scripts/%s.groovy", script));
        if (scriptFile.exists()) {
            log.info("Adding script to run queue: {}, current queue {}, current script {}", script, scriptQueue.size(), runningScript);
            scriptQueue.add(script);
            notifyRunner();
        }if (!scriptFile.exists()){
            log.info("Could not find the " + script + " script");
            msg(gui, "Could not find the " + script + " script", Color.RED);
        }
    }

    public void scheduleEvery(String script, Duration period) {
        gui = panel.findGameUI().orElse(null);
        File scriptFile = new File(String.format("scripts/%s.groovy", script));
        if (scriptFile.exists()) {
            synchronized (scheduledTasks) {
                log.info("Adding script to scheduler {} to run every {}", script, period);
                ScheduledFuture<?> fure = scheduler.scheduleAtFixedRate(() -> schedule(script), 0, period.toSeconds(), TimeUnit.SECONDS);
                scheduledTasks.put(script + "-every-" + period.toString().replace("PT", ""), fure);
            }
        }if (!scriptFile.exists()){
            log.info("Could not find the " + script + " script");
            msg(gui, "Could not find the " + script + " script", Color.RED);
        }
    }

    public void scheduleIn(String script, Duration offset) {
        gui = panel.findGameUI().orElse(null);
        File scriptFile = new File(String.format("scripts/%s.groovy", script));
        if (scriptFile.exists()) {
            synchronized (scheduledTasks) {
                log.info("Adding script to scheduler {} to run in {}", script, offset);
                String taskName = script + "-in-" + offset.toString();
                ScheduledFuture<?> future = scheduler.schedule(
                        () -> {
                            synchronized (scheduledTasks) {
                                scheduledTasks.remove(taskName);
                            }
                            schedule(script);
                        },
                        offset.toSeconds(), TimeUnit.SECONDS
                );
                scheduledTasks.put(taskName, future);
            }
        }if (!scriptFile.exists()){
            log.info("Could not find the " + script + " script");
            msg(gui, "Could not find the " + script + " script", Color.RED);
        }
    }

    public boolean cancel(String task) {
        boolean cancelled = Optional.ofNullable(scheduledTasks.get(task))
                .map(scheduledTask -> scheduledTask.cancel(false))
                .orElse(false);

        if (cancelled) {
            scheduledTasks.remove(task);
        }

        return cancelled;
    }

    // --------------------------------------------------
    // Script execution
    // --------------------------------------------------

    public void notifyRunner() {
        if (runningScript == null && scriptQueue.peek() != null) {
            ui = panel.getUI();
            gui = panel.findGameUI().orElse(null);
            String scriptToRun = scriptQueue.poll();
            log.info("Running script {}", scriptToRun);
            runScript(scriptToRun);
        }
    }

    public void runScript(String scriptName) {
        scriptExecutorService.execute(() -> {
            gui = panel.findGameUI().orElse(null);
            File scriptFile = new File(String.format("scripts/%s.groovy", scriptName));
            long beg = System.currentTimeMillis();

            if (scriptFile.exists()) {
                Logger log = LoggerFactory.getLogger(scriptName + "-script");

                BotEnvironment botEnvironment = new BotEnvironment(panel, ui, discord, this);

                ImportCustomizer importCustomizer = new ImportCustomizer();
                importCustomizer.addImports("java.awt.Color");
                importCustomizer.addImports("java.time.LocalDateTime");
                importCustomizer.addImports("java.time.temporal.ChronoUnit");

                CompilerConfiguration compilationCustomizer = new CompilerConfiguration();
                compilationCustomizer.addCompilationCustomizers(importCustomizer);

                Binding binding = new Binding();
                binding.setVariable("environment", botEnvironment);
                binding.setVariable("system", new BotSystem(botEnvironment));
                binding.setVariable("player", new BotPlayer(botEnvironment));
                binding.setVariable("inventory", new BotInventory(botEnvironment));
                binding.setVariable("log", log);
                binding.setVariable("vars", globalVariables);
                GroovyShell shell = new GroovyShell(binding, compilationCustomizer);

                msg(gui, String.format("'%s' script has started.", scriptName), Color.RED);
                try {
                    log.debug("Running script: {}", scriptFile);
                    runningScript = scriptName;
                    shell.evaluate(scriptFile);
                    msg(gui, String.format("'%s' script has finished execution.", scriptName), Color.RED);
                    botEnvironment.notifyFinishCallbacks();
                } catch (Exception e) {
                    log.error("Script ended due to exception", e);
                    msg(gui, "Script ended due to exception " + e.getMessage());
                    botEnvironment.notifyCrashCallbacks(e);
                } finally {
                    log.debug("Script {} execution ended after: {} ms", scriptName, System.currentTimeMillis() - beg);
                    runningScript = null;
                    notifyRunner();
                }
            }
            if (!scriptFile.exists()){
                msg(gui, "Could not find the " + scriptName + " script", Color.RED);
            }
        });
    }

    // --------------------------------------------------
    // Helper methods
    // --------------------------------------------------


    private void msg(GameUI gui, String message) {
        if (gui != null) {
            gui.msg(message);
        }
    }

    private void msg(GameUI gui, String message, Color color) {
        if (gui != null) {
            gui.msg(message, color);
        }
    }
}
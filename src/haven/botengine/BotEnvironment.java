package haven.botengine;

import haven.GameUI;
import haven.JOGLPanel;
import haven.RootWidget;
import haven.UI;
import haven.botengine.discord.Discord;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class BotEnvironment {

    protected static final Set<String> IGNORED_ITEMS = new HashSet<>(Arrays.asList("paltex", "male"));

    private final JOGLPanel panel;
    private final Discord discord;
    private final ScriptRunner scriptRunner;
    private GameUI gui;

    private RootWidget root;
    private UI ui;

    private List<Consumer<Exception>> crashCallbacks = new LinkedList<>();
    private List<Runnable> finishCallbacks = new LinkedList<>();

    public BotEnvironment(JOGLPanel panel, UI ui, Discord discord, ScriptRunner scriptRunner) {
        this.panel = panel;
        this.ui = ui;
        this.root = ui.root;
        Set<GameUI> guis = ui.root.children(GameUI.class);
        if (guis.iterator().hasNext())
            this.gui = guis.iterator().next();
        else
            this.gui = null;
        this.discord = discord;
        this.scriptRunner = scriptRunner;
    }

    public JOGLPanel getPanel() {
        return panel;
    }

    public Discord getDiscord() {
        return discord;
    }

    public GameUI getGui() {
        return gui;
    }

    public UI getUi() {
        return ui;
    }

    public RootWidget getRoot() {
        return root;
    }

    public void setGui(GameUI gui) {
        this.gui = gui;
    }

    public ScriptRunner getScriptRunner() {
        return scriptRunner;
    }

    // --------------------------------------------------
    // Callbacks
    // --------------------------------------------------

    public void addCrashCallback(Consumer<Exception> callback) {
        crashCallbacks.add(callback);
    }

    public void addFinishCallback(Runnable callback) {
        finishCallbacks.add(callback);
    }

    public void notifyCrashCallbacks(Exception ex) {
        crashCallbacks.forEach(callback -> callback.accept(ex));
    }

    public void notifyFinishCallbacks() {
        finishCallbacks.forEach(Runnable::run);
    }

}

package haven.commands;

import haven.*;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;

import java.io.File;

import static haven.botengine.config.BotConfig.isWindowless;

public class Makescreen implements Console.Command {

    private final Discord discord;
    private final ScriptRunner scriptRunner;
    private final UI ui;

    public Makescreen(UI ui, Discord discord, ScriptRunner scriptRunner) {
        this.discord = discord;
        this.scriptRunner = scriptRunner;
        this.ui = ui;
    }

    @Override
    public void run(Console cons, String[] args) {
        discordSendScreen("res/saved.png");
    }
    public void discordSendScreen(String file){
        if (!isWindowless()) {
            File scrn = new File("res/saved.png");
            if (scrn.exists()) {
                scrn.delete();
            }
            Screenshooter.take(ui.gui);
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
                discord.sendFile("Screen by command",file);
            }
        }
    }
    public boolean getUI() {
        for (Widget widget: ui.getRwidgets().keySet()) {
            if(widget.getClass().isAssignableFrom(GameUI.class) && widget.visible && widget.hasfocus){
                return true;
            }
        }
        return false;
    }
}
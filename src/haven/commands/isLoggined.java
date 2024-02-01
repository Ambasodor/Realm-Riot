package haven.commands;

import haven.Console;
import haven.GameUI;
import haven.UI;
import haven.Widget;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;

public class isLoggined implements Console.Command {

    private final Discord discord;
    private final ScriptRunner scriptRunner;
    private final UI ui;

    public isLoggined(UI ui, Discord discord, ScriptRunner scriptRunner) {
        this.discord = discord;
        this.scriptRunner = scriptRunner;
        this.ui = ui;
    }

    @Override
    public void run(Console cons, String[] args) {
        StringBuilder tasksMessage = new StringBuilder("Script: ");
        tasksMessage.append(scriptRunner.getRunningScript().orElse("none"));


         boolean ui = getUI();
        if (ui == false){
            tasksMessage.append(" - ").append("Im not ingame");
        }
        if (ui == true){
            tasksMessage.append(" - ").append("Im ingame");
        }

        discord.respond(tasksMessage.toString());

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
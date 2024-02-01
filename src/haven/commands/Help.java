package haven.commands;

import haven.Console;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;

public class Help implements Console.Command {

    private final Discord discord;
    private final ScriptRunner scriptRunner;

    public Help(Discord discord, ScriptRunner scriptRunner) {
        this.discord = discord;
        this.scriptRunner = scriptRunner;
    }

    @Override
    public void run(Console cons, String[] args) {
        StringBuilder tasksMessage = new StringBuilder();

        tasksMessage.append("Commands:\nrun [scriptname] (script runner)\nprintresources (gonna printresources in console)\nschedule (show schedule)\nrunning (shows running scripts)\ningame? (shows if char ingame)\nscreen (do screenshot and post)\ncancel [scriptname] (check schedule command for scriptname)");

        discord.respond(tasksMessage.toString());

    }
}
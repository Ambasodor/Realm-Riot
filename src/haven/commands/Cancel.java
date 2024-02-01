package haven.commands;

import haven.Console;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;

public class Cancel implements Console.Command {

    private final Discord discord;
    private final ScriptRunner scriptRunner;

    public Cancel(Discord discord, ScriptRunner scriptRunner) {
        this.discord = discord;
        this.scriptRunner = scriptRunner;
    }

    @Override
    public void run(Console cons, String[] args) {
        if (args.length < 2) {
            discord.respond("Provide a scheduled task to cancel");
        }
        String taskName = args[1];

        if (scriptRunner.cancel(taskName)) {
            discord.respond("Task " + taskName + " has been removed.");
        } else {
            discord.respond("Scheduled task " + taskName + " was not found.");
        }
    }

}

package haven.commands;

import haven.Console;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;

public class RunningScriptToDiscord implements Console.Command {

    private final Discord discord;
    private final ScriptRunner scriptRunner;


    public RunningScriptToDiscord(Discord discord, ScriptRunner scriptRunner) {
        this.discord = discord;
        this.scriptRunner = scriptRunner;
    }

    @Override
    public void run(Console cons, String[] args) {
        StringBuilder tasksMessage = new StringBuilder("Currently running script: ");
        tasksMessage.append(scriptRunner.getRunningScript().orElse("none"));

        discord.respond(tasksMessage.toString());
    }
}
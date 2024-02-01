package haven.commands;

import haven.Console;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledTasksToDiscord implements Console.Command  {

    private final Discord discord;
    private final ScriptRunner scriptRunner;

    public ScheduledTasksToDiscord(Discord discord, ScriptRunner scriptRunner) {

        this.discord = discord;
        this.scriptRunner = scriptRunner;
    }

    @Override
    public void run(Console cons, String[] args) {
        Map<String, ScheduledFuture<?>> tasks = scriptRunner.getScheduledTasks();

        StringBuilder tasksMessage = new StringBuilder("Currently scheduled tasks: ");

        if (tasks.size() > 0) {
            tasksMessage.append(tasks.size()).append(". \n");
            tasks.forEach((name, future) ->
                tasksMessage.append(name).append(" (will run in: ").append(formatRemainingTime(future)).append(")\n")
            );
        } else {
            tasksMessage.append("none.");
        }

        discord.respond(tasksMessage.toString());
    }

    private String formatRemainingTime(ScheduledFuture<?> future) {
        long minutes = future.getDelay(TimeUnit.MINUTES);
        if (minutes > 10) {
            return minutes + " minutes";
        } else {
            return future.getDelay(TimeUnit.SECONDS) + " seconds";
        }
    }

}


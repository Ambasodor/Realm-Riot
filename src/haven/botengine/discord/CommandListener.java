package haven.botengine.discord;

import haven.JOGLPanel;
import haven.LWJGLPanel;
import haven.botengine.ScriptRunner;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import static java.lang.System.*;

public class CommandListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(CommandListener.class);

    private final String commandChannel;
    private final Set<String> allowedUsers;
    private final JDA jda;
    private final JOGLPanel panel;
    public static String scrnm = null;

    private String myId;

    public CommandListener(JDA jda, JOGLPanel panel, String commandChannel, Set<String> allowedUsers) {
        this.jda = jda;
        this.panel = panel;
        this.commandChannel = commandChannel;
        this.allowedUsers = allowedUsers;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean correctChannel = fromCorrectChannel(event);
        boolean correctUser = fromCorrectUser(event);
        boolean highlightedMe = highlightedMe(event);

        if (correctChannel && correctUser && highlightedMe) {
            handleCommand(event);
        }
    }

    private void handleCommand(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw()
                .replace(getHighlightedRawId(), "")
                .replace(getMobileHighlightedRawId(), "")
                .trim();

        String[] args = message.split(" ");
        String command = args[0];

        log.debug("Handle command {}", message);
        if (panel.findcmds().containsKey(command)) {
            try {
                panel.findcmds().get(command).run(null, args);
                if ("run".equals(args[0]) && args.length < 3) {
                    respondWithRunInformation(event.getChannel());
                } else if ("run".equals(args[0]) && args.length == 4) {
                    respondWithScheduledRunInformation(event.getChannel());
                }
            } catch (Exception e) {
                log.error("Running a command from Discord caused an exception.", e);
            }
        } else {
            respondWithError(event.getChannel(), message);
        }
    }

    private void respondWithRunInformation(MessageChannel channel) {
        ScriptRunner scriptRunner = panel.getScriptRunner();
        Queue<String> tasks = scriptRunner.getScriptQueue();
        
        StringBuilder tasksMessage = new StringBuilder("Currently running task: ");
        tasksMessage.append(scriptRunner.getRunningScript().orElse("none"));

        if (tasks.size() > 0) {
            tasksMessage.append(". Waiting tasks: ");
            tasksMessage.append(tasks.size());
            tasksMessage.append(". ");
            tasksMessage.append(tasks.stream().collect(Collectors.joining(", ", "[", "].")));
        }

        channel.sendMessage(tasksMessage.toString()).submit();
    }

    private void respondWithScheduledRunInformation(MessageChannel channel) {
        ScriptRunner scriptRunner = panel.getScriptRunner();
        Map<String, ScheduledFuture<?>> tasks = scriptRunner.getScheduledTasks();

        StringBuilder tasksMessage = new StringBuilder("Currently scheduled tasks: ");

        if (tasks.size() > 0) {
            tasksMessage.append(tasks.size());
            tasksMessage.append(". ");
            tasksMessage.append(tasks.keySet().stream().collect(Collectors.joining(", ", "[", "].")));
        } else {
            tasksMessage.append("none.");
        }

        channel.sendMessage(tasksMessage.toString()).submit();
    }

    private void respondWithError(MessageChannel channel, String message) {
        log.debug("Received a wrong command: {}, sending information about it to: {}", message, channel.getName());
        channel.sendMessage("Command (" + message + ") was not understood by the bot.").submit();
    }

    // --------------------------------------------------
    // Event verification
    // --------------------------------------------------

    private boolean fromCorrectChannel(MessageReceivedEvent event) {
        return commandChannel.equals(event.getChannel().getName());
    }

    private boolean fromCorrectUser(MessageReceivedEvent event) {
        return allowedUsers.contains(event.getAuthor().getName());
    }

    private boolean highlightedMe(MessageReceivedEvent event) {
        return event.getMessage().getContentRaw().contains(getMyId());
    }

    private String getHighlightedRawId() {
        return "<@!" + getMyId() + ">";
    }

    private String getMobileHighlightedRawId() {
        return "<@" + getMyId() + ">";
    }

    private String getMyId() {
        if (myId == null) {
            myId = jda.getSelfUser().getId();
        }

        return myId;
    }

}

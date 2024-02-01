package haven.botengine.discord;

import haven.JOGLPanel;
import haven.LWJGLPanel;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static haven.OptWnd.ChannelnameString;

public class Discord {

    public static final String DISCORD_CONFIG_FILE_LOCATION = "config/discord.properties";
    private static MessageChannel msgchan;
    private final List<TextChannel> notificationChannels;
    private final List<TextChannel> commandChannels;
    public JDA jda;


    public Discord(JOGLPanel panel) {

        try {
            DiscordSettings settings = new DiscordSettings(DISCORD_CONFIG_FILE_LOCATION);

            if (!"".equals(settings.getToken())) {
                 jda = JDABuilder.createDefault(settings.getToken())
                        .build();

                jda.addEventListener(
                        new CommandListener(
                                jda,
                                panel,
                                settings.getCommandsChannel(),
                                settings.getAllowedUsers())
                );

                jda.awaitReady();
                notificationChannels = jda.getTextChannelsByName(settings.getMessagesChannel(), true);
                commandChannels = jda.getTextChannelsByName(settings.getCommandsChannel(), true);
            } else {
                notificationChannels = Collections.emptyList();
                commandChannels = Collections.emptyList();
            }
        } catch (LoginException | InterruptedException | IOException e) {
            throw new RuntimeException("Could not initialize Discord", e);
        }

    }

    public void sendMessage(String message) {
        notificationChannels.forEach(channel ->
                channel.sendMessage(message).submit()
        );
    }

    public void sendFile(String message, String filepath){
        notificationChannels.forEach(channel ->
                channel.sendMessage(message).addFile(new File(filepath)).queue()
        );
    }

    public void sendFile(String path){
        File file = new File(path);
        notificationChannels.forEach(channel ->
                channel.sendMessage("Here is my screenshot!").addFile(file).queue()
        );
    }


    public void respond(String message) {
        commandChannels.forEach(channel ->
                channel.sendMessage(message).submit()
        );
    }

    public void setEmodji(){
        notificationChannels.forEach(channel ->
                channel.getHistory().retrievePast(2)
                        .map(messages -> messages.get(0)) // this assumes that the channel has at least 2 messages
                        .queue(message -> { // success callback
                            System.out.println("Last message in NotificationChannel is: " + message.getContentDisplay());
                            List<MessageReaction> reactions = message.getReactions();
                            if (reactions.size() > 0){
                                message.clearReactions();
                                System.out.println("not null");
                                message.addReaction("U+1F35E").queue();
                            }
                            if (reactions.size() == 0) {
                                System.out.println("null");
                                message.addReaction("U+1F35E").queue();
                            }
                        })
        );
    }
}

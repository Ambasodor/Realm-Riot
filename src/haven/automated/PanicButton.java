package haven.automated;


import haven.*;
import haven.botengine.BotEnvironment;
import haven.botengine.BotPlayer;
import haven.botengine.discord.Discord;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static haven.Config.playername;
import static haven.MainFrame.tent;
import static haven.OCache.posres;
import static haven.OptWnd.ChannelnameString;
import static haven.OptWnd.MessagetochannelString;
import static haven.botengine.config.BotConfig.isWindowless;
import static haven.botengine.discord.DiscordSettings.MESSAGES_CHANNEL_PROPERTY;

public class PanicButton implements Runnable {
    private final UI ui;
    private final Discord discord;
    public List<TextChannel> alarmChannel;

    public PanicButton(UI ui, Discord discord) {
        this.discord = discord;
        this.ui = ui;
    }

    @Override
    public void run() {
        StringBuilder tasksMessage = new StringBuilder();
        tasksMessage.append("<@&1177320218889048114> ").append(playername).append(" NEED HELP! PANIC BUTTON PRESSED!");
        //System.out.println("pressed");
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
                sendAlarm(file);
            }
        }
    }

    public void sendAlarm(String filepath){
        StringBuilder tasksMessage = new StringBuilder();
        tasksMessage.append("<@&1177320218889048114> ").append(playername).append(" ").append(MessagetochannelString);
        alarmChannel = discord.jda.getTextChannelsByName(ChannelnameString, true);
        alarmChannel.forEach(channel ->
                channel.sendMessage(tasksMessage).addFile(new File(filepath)).queue()
        );
    }
    public void stop() {
        if (ui.gui.PanicButtonThread != null) {
            ui.gui.PanicButtonThread.interrupt();
            ui.gui.PanicButtonThread = null;
        }
    }
    public void sleep() throws InterruptedException {
        Thread.sleep(5000);
    }
}

package haven.commands;

import haven.Console;
import haven.GameUI;
import haven.UI;
import haven.Widget;
import haven.botengine.ScriptRunner;
import haven.botengine.discord.Discord;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static haven.JOGLPanel.discordjava;
import static haven.LoginScreen.loginshare;
import static java.awt.Color.red;

public class KickifBanned implements Runnable {
    private final UI ui;
    private final Widget widget;


    public KickifBanned(UI ui, Widget widget) {
        this.ui = ui;
        this.widget = widget;
    }

    @Override
    public void run() {
        StringBuilder tasksMessage = new StringBuilder("Login Data: ");

        if (ui == null){
            tasksMessage.append("").append("No banned users playing");
        }
        if (ui != null){
            if (userbanned()) {
                tasksMessage.append("").append(loginshare + " black listed user ingame, disconnecting");
                ui.destroy(widget);
                System.out.println("You were banned by client developer");
            }
            if (!userbanned()){
                tasksMessage.append(loginshare + " user are loggined in, he is not blacklisted");
            }
        }
            Consumer<? super TextChannel> action = channel ->
                    channel.sendMessage(tasksMessage).queue();
            Objects.requireNonNull(action);
            for (net.dv8tion.jda.api.entities.TextChannel t : getchannelbyname("banned-logins-log")) {
                action.accept(t);
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

    public List<TextChannel> getchannelbyname(String name){
        return discordjava.jda.getTextChannelsByName(name, true);
    }
    public boolean userbanned() {
        MessageHistory history = MessageHistory.getHistoryFromBeginning(getchannelbyname("banned-logins").get(0)).complete();
        List<net.dv8tion.jda.api.entities.Message> messi = history.getRetrievedHistory();
        if (!messi.isEmpty()) {
            for (int i = 0; i < messi.size(); i++) {
                if (messi.get(i).toString().contains(loginshare)) {
                    return true;
                }
            }
        }
        return false;
    }
}
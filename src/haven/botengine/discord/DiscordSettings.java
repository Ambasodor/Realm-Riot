package haven.botengine.discord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiscordSettings {

    private static final Logger log = LoggerFactory.getLogger(DiscordSettings.class);

    public static final String TOKEN_PROPERTY = "token";
    public static final String MESSAGES_CHANNEL_PROPERTY = "messages-channel";
    public static final String COMMANDS_CHANNEL_PROPERTY = "commands-channel";
    public static final String ALLOWED_USERS_PROPERTY = "allowed-users";

    private String token;
    private String messagesChannel;
    private String commandsChannel;
    private Set<String> allowedUsers;

    public DiscordSettings(String fileLocation) throws IOException {
        InputStream input = new FileInputStream(fileLocation);
        Properties discordProperties = new Properties();
        discordProperties.load(input);

        token = discordProperties.getProperty(TOKEN_PROPERTY);

        messagesChannel = discordProperties.getProperty(MESSAGES_CHANNEL_PROPERTY);
        log.debug("Configuring channel for messages {}", messagesChannel);

        commandsChannel = discordProperties.getProperty(COMMANDS_CHANNEL_PROPERTY);
        log.debug("Configuring channel for commands {}", commandsChannel);

        allowedUsers = propertyToSet(discordProperties.getProperty(ALLOWED_USERS_PROPERTY));
        log.debug("Configuring allowed users {}", allowedUsers);
    }

    // --------------------------------------------------
    // Available settings
    // --------------------------------------------------

    public String getToken() {
        return token;
    }

    public String getMessagesChannel() {
        return messagesChannel;
    }

    public String getCommandsChannel() {
        return commandsChannel;
    }

    public Set<String> getAllowedUsers() {
        return allowedUsers;
    }

    // --------------------------------------------------
    // Property helpers
    // --------------------------------------------------

    private Set<String> propertyToSet(String property) {
        return Stream.of(property.trim().split(","))
                .map(String::trim)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

}
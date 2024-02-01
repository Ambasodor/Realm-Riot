package haven.botengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class BotSystem {

    private static final Logger log = LoggerFactory.getLogger(BotSystem.class);
    private final BotEnvironment environment;

    public BotSystem(BotEnvironment environment) {
        this.environment = environment;
    }

    public void msg(String message) {
        environment.getGui().msg(message);
        log(message);
    }

    public void msg(String message, Color color) {
        environment.getGui().msg(message, color);
        log(message);
    }

    private void log(String message) {
        log.trace("Sending GUI message: {}", message);
    }

}

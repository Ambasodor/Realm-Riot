package haven.botengine;

import haven.JOGLPanel;
import haven.commands.RunScript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DefaultCommands {

    private static final Logger log = LoggerFactory.getLogger(DefaultCommands.class);
    private static final String COMMANDS_FILE_NAME = "commands.txt";

    public static void run(JOGLPanel panel, ScriptRunner scriptRunner) {
        try (Stream<String> stream = Files.lines(Paths.get(COMMANDS_FILE_NAME))) {
            stream.forEach( command -> new RunScript(panel, scriptRunner).run(null, command.split(" ")));
        } catch (IOException e) {
            log.error("Could not read the default commands", e);
        }
    }

}

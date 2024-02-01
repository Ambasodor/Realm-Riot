package haven.botengine.config;

import haven.MainFrame;

public class BotConfig {

    public static boolean isWindowless() {
        if ("true".equals(System.getProperty("windowless"))) {
            MainFrame.renderer.set("null");
        }
        return "true".equals(System.getProperty("windowless"));
    }

}

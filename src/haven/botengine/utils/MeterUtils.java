/*
package haven.botengine.utils;

import haven.*;
import haven.botengine.BotEnvironment;
import haven.botengine.entities.Health;

import java.time.Duration;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MeterUtils {

    private static final String STAMINA_RESOURCE_BASENAME = "stam";
    private static final Pattern STAMINA_TOOLTIP_PATTERN = Pattern.compile("^Stamina: (\\d+)%$");

    private static final String ENERGY_RESOURCE_BASENAME = "nrj";
    private static final Pattern ENERGY_TOOLTIP_PATTERN = Pattern.compile("^Energy: (\\d+)%.*");

    private static final String HEALTH_RESOURCE_BASENAME = "hp";
    private static final Pattern HEALTH_TOOLTIP_PATTERN = Pattern.compile("^Health: (\\d+)/(\\d+)/(\\d+)$");

    public static int getStamina(BotEnvironment environment) {
        return parsePercentages(getIMeter(environment.getGui(), STAMINA_RESOURCE_BASENAME), STAMINA_TOOLTIP_PATTERN);
    }

    public static int getEnergy(BotEnvironment environment) {
        return parsePercentages(getIMeter(environment.getGui(), ENERGY_RESOURCE_BASENAME), ENERGY_TOOLTIP_PATTERN);
    }

    public static Health getHealth(BotEnvironment environment) {
        return parseHealth(getIMeter(environment.getGui(), HEALTH_RESOURCE_BASENAME));
    }

    public static boolean waitForBars(BotEnvironment environment, Duration sleepTime, int retries) {
        boolean foundBars = false;

        for (int i = 0; i < retries; i++) {
            foundBars = hasAllBars(environment.getGui());
            if (foundBars) {
                break;
            } else {
                try {
                    Thread.sleep(sleepTime.toMillis());
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ex);
                }
            }
        }

        return foundBars;
    }

    private static IMeter getIMeter(GameUI gui, String resourceBasename) {
        return gui.children(IMeter.class).stream()
                .filter(iMeter -> iMeter.getResource().isPresent())
                .filter(iMeter -> resourceBasename.equals(iMeter.getResource().get().basename()))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Meter " + resourceBasename + " not found!"));
    }

    private static int parsePercentages(IMeter iMeter, Pattern pattern) {
        return parseTooltipAndTransform(iMeter, pattern, matcher -> Integer.parseInt(matcher.group(1)));
    }

    private static Health parseHealth(IMeter iMeter) {
        return parseTooltipAndTransform(iMeter, HEALTH_TOOLTIP_PATTERN, matcher -> {
            int soft = Integer.parseInt(matcher.group(1));
            int hard = Integer.parseInt(matcher.group(2));
            int max = Integer.parseInt(matcher.group(3));
            return new Health(soft, hard, max);
        });
    }

    private static <T> T parseTooltipAndTransform(IMeter iMeter, Pattern pattern, Function<Matcher, T> transformer) {
        String tooltipText = getMeterTooltip(iMeter);
        Matcher matcher = pattern.matcher(tooltipText);
        if (matcher.matches()) {
            return transformer.apply(matcher);
        } else {
            throw new RuntimeException("Could not parse the meter tooltip \"" + tooltipText + "\"");
        }
    }

    private static String getMeterTooltip(IMeter iMeter) {
        return ((Text.Line) iMeter.tooltip).text;
    }

    private static boolean hasAllBars(GameUI gui) {
        Set<String> meterNames = gui.children(IMeter.class).stream()
                .filter(iMeter -> iMeter.getResource().isPresent())
                .map(iMeter -> iMeter.getResource().get().basename())
                .collect(Collectors.toSet());

        return meterNames.contains(STAMINA_RESOURCE_BASENAME)
                && meterNames.contains(HEALTH_RESOURCE_BASENAME)
                && meterNames.contains(HEALTH_RESOURCE_BASENAME);
    }

}

*/
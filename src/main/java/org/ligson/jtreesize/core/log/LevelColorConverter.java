package org.ligson.jtreesize.core.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.DynamicConverter;

public class LevelColorConverter extends DynamicConverter<ILoggingEvent> {

    // ANSI 转义字符，用于控制颜色
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String MAGENTA = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    @Override
    public String convert(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            case Level.ERROR_INT:
                return RED + level.toString() + RESET;
            case Level.WARN_INT:
                return YELLOW + level.toString() + RESET;
            case Level.INFO_INT:
                return GREEN + level.toString() + RESET;
            case Level.DEBUG_INT:
                return BLUE + level.toString() + RESET;
            case Level.TRACE_INT:
                return MAGENTA + level.toString() + RESET;
            default:
                return CYAN + level.toString() + RESET;
        }
    }
}

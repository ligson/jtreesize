package org.ligson.jtreesize.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.DynamicConverter;

public class LoggerColorConverter extends DynamicConverter<ILoggingEvent> {
    private static final String RESET = "\u001B[0m";
    private static final String MAGENTA = "\u001B[35m";  // 类名使用紫色

    @Override
    public String convert(ILoggingEvent event) {
        return MAGENTA + event.getLoggerName() + RESET;  // 给类名加上颜色
    }
}

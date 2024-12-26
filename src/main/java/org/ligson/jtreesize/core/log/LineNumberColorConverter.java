package org.ligson.jtreesize.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.DynamicConverter;

public class LineNumberColorConverter extends DynamicConverter<ILoggingEvent> {
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";  // 行号使用黄色

    @Override
    public String convert(ILoggingEvent event) {
        return YELLOW + event.getCallerData()[0].getLineNumber() + RESET;  // 给行号加上颜色
    }
}

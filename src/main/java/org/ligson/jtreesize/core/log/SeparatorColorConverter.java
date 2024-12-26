package org.ligson.jtreesize.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.DynamicConverter;

public class SeparatorColorConverter extends DynamicConverter<ILoggingEvent> {
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";  // 给分隔符使用蓝色

    @Override
    public String convert(ILoggingEvent event) {
        return BLUE + ">>> " + RESET;  // 为分隔符添加颜色
    }
}

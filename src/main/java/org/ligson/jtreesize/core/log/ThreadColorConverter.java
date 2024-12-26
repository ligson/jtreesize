package org.ligson.jtreesize.core.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.DynamicConverter;

public class ThreadColorConverter extends DynamicConverter<ILoggingEvent> {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";  // 线程名称使用青色

    @Override
    public String convert(ILoggingEvent event) {
        return CYAN + event.getThreadName() + RESET;  // 给线程名称加上颜色
    }
}

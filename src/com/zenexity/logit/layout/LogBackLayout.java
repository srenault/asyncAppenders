package com.zenexity.logit.layout;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class LogbackLayout extends LayoutBase<ILoggingEvent>{

    @Override
    public String doLayout(ILoggingEvent e) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        StackTraceElement ste = e.getCallerData()[0];
        jsonMap.put("logger", e.getLoggerName());
        jsonMap.put("className", ste.getClassName());
        jsonMap.put("date", new Long(e.getTimeStamp()));
        jsonMap.put("file", ste.getFileName());
        jsonMap.put("location", ste.toString());
        jsonMap.put("line", new Integer(ste.getLineNumber()));
        jsonMap.put("method", ste.getMethodName());
        jsonMap.put("message", e.getFormattedMessage());
        jsonMap.put("level", e.getLevel().levelStr);
        jsonMap.put("thread", e.getThreadName());
        return new Gson().toJson(jsonMap);
    }
}

package com.zenexity.logit.layout;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import com.google.gson.Gson;

public class Log4JLayout extends Layout {

	@Override
	public void activateOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String format(LoggingEvent e) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("logger", e.getLoggerName());
		jsonMap.put("class", e.getFQNOfLoggerClass());
		jsonMap.put("date", new Long(e.getTimeStamp()).toString());
		jsonMap.put("file", e.getLocationInformation().getFileName());
		jsonMap.put("location", e.getLocationInformation().fullInfo);
		jsonMap.put("line", e.getLocationInformation().getLineNumber());
		jsonMap.put("method", e.getLocationInformation().getMethodName());
		jsonMap.put("message", e.getMessage().toString());
		jsonMap.put("level", e.getLevel().toString());
		jsonMap.put("thread", e.getThreadName());
		return new Gson().toJson(jsonMap);
	}

	@Override
	public boolean ignoresThrowable() {
		// TODO Auto-generated method stub
		return false;
	}

}

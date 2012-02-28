package com.zenexity.logit.appender;

import java.util.Map;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.generators.ByteArrayBodyGenerator;

public class LogbackAppender extends AppenderBase<ILoggingEvent> {

    private String server = null;
    private String project = null;

    private Layout<ILoggingEvent> layout;
    
    public void setLayout(Layout<ILoggingEvent> layout) {
        this.layout = layout;
    }

    @Override
    public void start() {
      if (this.layout == null) {
        addError("No layout set for the appender named ["+ name +"].");
        return;
      }
      super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        String json = this.layout.doLayout(event);
    	Gson gson = new Gson();
    	Map<String, String> jsonMap = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
    	jsonMap.put("project", this.project);
    	json = gson.toJson(jsonMap);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = this.server + "/story/eval";
        try {
        	System.out.println(client.preparePost(url)
        	      .addHeader("Content-type", "application/json")
        		  .setBody(new ByteArrayBodyGenerator(json.getBytes())).execute());
        } catch (Exception e) {
        	e.printStackTrace();
        }	
    }

	public void setServer(String server) {
		this.server = server;
	}

	public void setProject(String project) {
		this.project = project;
	}
}

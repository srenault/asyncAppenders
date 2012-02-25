package com.zenexity.logit.appender;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.generators.ByteArrayBodyGenerator;
import com.ning.http.client.generators.InputStreamBodyGenerator;
//import com.ning.http.client.generators.
public class Log4jAppender extends AppenderSkeleton {

    private String server = null;
    private String project = null;

    public Log4jAppender() {
        super();
    }
   
    @Override
    public void close() {
        // TODO Auto-generated method stub
    }
    
    @Override
    public boolean requiresLayout() {
        return true;
    }

    @Override
    protected void append(LoggingEvent event) {
    	String json = super.layout.format(event);
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

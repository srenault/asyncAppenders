package com.zenexity.logit.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

import com.ning.http.client.AsyncHttpClient;

public class LogbackAppender extends AppenderBase<ILoggingEvent> {

    private String server = null;

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
        AsyncHttpClient client = new AsyncHttpClient();    
        String url = this.server + "/story/eval";
        
        try {
	      client.preparePost(url)
	        	.addHeader("Accept", "application/json")
	        	.addHeader("Content-type", "application/json")
	  		  	.setBody(json).execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

	public void setServer(String server) {
		this.server = server;
	}
}

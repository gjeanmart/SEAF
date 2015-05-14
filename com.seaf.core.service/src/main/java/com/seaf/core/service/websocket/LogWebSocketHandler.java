package com.seaf.core.service.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.seaf.core.service.business.LoggerService;

public class LogWebSocketHandler  extends TextWebSocketHandler {

	  @Autowired
	  private LoggerService loggerService;
	  
	  @Override
	  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	    loggerService.registerOpenConnection(session);
	  }
	  
	  @Override
	  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		  loggerService.registerCloseConnection(session);
	    
	  }
	  
	  @Override
	  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		  loggerService.registerCloseConnection(session);
	  }	  
	  
	  @Override
	  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		  if(message.getPayload().startsWith("start ")) {
		  		loggerService.startTailerOnFile(session, message.getPayload().substring(6, message.getPayload().length()));
		  } else {
			  	loggerService.stopTailerOnFile(session, message.getPayload().substring(5, message.getPayload().length()));
		  }
	  }
}

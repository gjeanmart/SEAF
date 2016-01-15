package com.seaf.core.connector.websocket;

import com.seaf.core.service.business.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class KeyWebSocketTailer {
	
	private WebSocketSession 	session;
	private String 				logFile;

	public KeyWebSocketTailer(WebSocketSession session, String logFile) {
		this.session = session;
		this.logFile = logFile;
	}
	
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public String getLogFile() {
		return logFile;
	}
	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	@Override
	public String toString() {
		return "KeyWebSocketTailer [session=" + session + ", logFile=" + logFile + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logFile == null) ? 0 : logFile.hashCode());
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyWebSocketTailer other = (KeyWebSocketTailer) obj;
		if (logFile == null) {
			if (other.logFile != null)
				return false;
		} else if (!logFile.equals(other.logFile))
			return false;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (!session.equals(other.session))
			return false;
		return true;
	}


	public static class LogWebSocketHandler  extends TextWebSocketHandler {

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
}

package com.seaf.core.service.websocket;

import org.springframework.web.socket.WebSocketSession;

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
	
	
}

package es.codeurjc.eoloplanner.server.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.codeurjc.eoloplanner.server.model.EoloPlantResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

	Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

	Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		logger.info("Sending message via web socket: " + message.getPayload());
		
		session.sendMessage(new TextMessage(message.getPayload()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("User disconnected "+session.getId());

		webSocketSessionMap.remove(session.getId());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("User connected "+session.getId());

		webSocketSessionMap.put(session.getId(), session);

		session.sendMessage(new TextMessage("Hi"));
	}

	public Map<String, WebSocketSession> getWebSocketSessionMap() {
		return webSocketSessionMap;
	}
}
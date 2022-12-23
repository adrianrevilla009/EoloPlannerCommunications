package es.codeurjc.eoloplanner.server.ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.codeurjc.eoloplanner.server.model.EoloPlantResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
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

		logger.info("Message received:" + message.getPayload());
		// test response data
		EoloPlantResponse eoloPlantResponse = new EoloPlantResponse
				(1L, "Madrid", null, 50, false);
		
		session.sendMessage(new TextMessage(eoloPlantResponse.toJson()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("User disconnected "+session.getId());
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("User connected "+session.getId());

		webSocketSessionMap.put(session.getId(), session);
		
		session.sendMessage(new TextMessage("Hello user"));
	}

}
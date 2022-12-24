package es.codeurjc.eoloplanner.server.controller;


import es.codeurjc.eoloplanner.server.model.EoloPlant;
import es.codeurjc.eoloplanner.server.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.server.service.EoloPlantResponseService;
import es.codeurjc.eoloplanner.server.service.EoloPlantService;
import es.codeurjc.eoloplanner.server.ws.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
public class EoloPlantController {

	private EoloPlantService eoloPlants;

	private WebSocketHandler webSocketHandler;

	private EoloPlantResponseService eoloPlantResponseService;

	public EoloPlantController(EoloPlantService eoloPlants, WebSocketHandler webSocketHandler,
							   EoloPlantResponseService eoloPlantResponseService) {
		this.eoloPlants = eoloPlants;
		this.webSocketHandler = webSocketHandler;
		this.eoloPlantResponseService = eoloPlantResponseService;
	}

	Logger logger = LoggerFactory.getLogger(EoloPlantController.class);

	@QueryMapping
	public Collection<EoloPlant> eoloPlants() {
		return eoloPlants.findAll();
	}

	@QueryMapping
	public Optional<EoloPlant> eoloPlant(@Argument long id) {
		return eoloPlants.findById(id);
	}

	@MutationMapping
	public EoloPlant createEoloPlant(@Argument EoloPlant eoloPlant) throws ExecutionException, InterruptedException, IOException {
		EoloPlant savedEoloPlant = eoloPlants.createEoloplant(eoloPlant);

		this.sendWebSocketResponse(new EoloPlantResponse(savedEoloPlant.getId(), eoloPlant.getCity(), eoloPlant.getPlanning(), 0, false));

		return savedEoloPlant;
	}

	@MutationMapping
	public EoloPlant deleteEoloPlant(@Argument long id) {
		return eoloPlants.deleteById(id);
	}

	@StreamListener(Sink.INPUT)
	public void handle(EoloPlantResponse eoloPlantResponse) throws ExecutionException, InterruptedException, IOException {
		this.logger.info("Receiving " + eoloPlantResponse.getProgress() + "% progress event");

		this.eoloPlantResponseService.save(eoloPlantResponse);

		this.sendWebSocketResponse(eoloPlantResponse);
	}

	private void sendWebSocketResponse(EoloPlantResponse eoloPlantResponse) throws IOException, InterruptedException {
		for (Map.Entry<String, WebSocketSession> sessionEntry :this.webSocketHandler.getWebSocketSessionMap().entrySet()) {
			this.webSocketHandler.handleTextMessage(sessionEntry.getValue(), new TextMessage(eoloPlantResponse.toJson()));
		}
	}
}

package es.codeurjc.eoloplanner.server.controller;


import es.codeurjc.eoloplanner.server.model.EoloPlant;
import es.codeurjc.eoloplanner.server.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.server.service.EoloPlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Controller
public class EoloPlantController {

	@Autowired
	private EoloPlantService eoloPlants;

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
	public EoloPlant createEoloPlant(@Argument EoloPlant eoloPlant) throws ExecutionException, InterruptedException {
		return eoloPlants.createEoloplant(eoloPlant);
	}

	@MutationMapping
	public EoloPlant deleteEoloPlant(@Argument long id) {
		return eoloPlants.deleteById(id);
	}

	@StreamListener(Sink.INPUT)
	public void handle(EoloPlantResponse eoloPlantResponse) throws ExecutionException, InterruptedException {
		this.logger.info("Receiving " + eoloPlantResponse.getProgress() + "% progress event");
		
	}
}

package es.codeurjc.eoloplanner.planner.controller;

import es.codeurjc.eoloplanner.planner.service.EoloPlantService;
import es.codeurjc.eoloplanner.planner.model.EoloPlant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;


@Controller
public class EoloPlantController {

	@Autowired
	private EoloPlantService eoloPlants;

	Logger logger = LoggerFactory.getLogger(EoloPlantController.class);

	@StreamListener(Sink.INPUT)
	public void handle(EoloPlant eoloPlant) throws ExecutionException, InterruptedException {
		this.logger.info("Event received: " + eoloPlant);
		this.eoloPlants.createEoloplant(eoloPlant);
	}
}

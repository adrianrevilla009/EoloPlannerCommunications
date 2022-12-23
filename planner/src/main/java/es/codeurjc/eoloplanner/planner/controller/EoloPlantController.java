package es.codeurjc.eoloplanner.planner.controller;

import es.codeurjc.eoloplanner.planner.service.EoloPlantService;
import es.codeurjc.eoloplanner.planner.model.EoloPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Controller;


@Controller
public class EoloPlantController {

	@Autowired
	private EoloPlantService eoloPlants;

	@StreamListener(Sink.INPUT)
	public void handle(EoloPlant eoloPlant) {
		System.out.println("Event received: " + eoloPlant);
	}
}

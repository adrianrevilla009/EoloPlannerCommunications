package es.codeurjc.eoloplanner.server.controller;

import es.codeurjc.eoloplanner.server.service.EoloPlantService;
import es.codeurjc.eoloplanner.server.model.EoloPlant;
import org.springframework.beans.factory.annotation.Autowired;
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

	@QueryMapping
	public Collection<EoloPlant> eoloPlants() {
		Collection<EoloPlant> a = eoloPlants.findAll();
		return a;
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
}

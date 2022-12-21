package es.codeurjc.eoloplanner.planner.service;

import es.codeurjc.eoloplanner.planner.model.EoloPlant;
import es.codeurjc.eoloplanner.planner.repository.EoloPlantRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SampleDatabaseLoader {

	@Autowired 
	private EoloPlantRepository posts;
	
	@PostConstruct
	public void init() {
		posts.save(new EoloPlant("Madrid", "madrid-sunny-flat"));
	}
}

package es.codeurjc.eoloplanner.server.service;

import es.codeurjc.eoloplanner.server.model.EoloPlant;
import es.codeurjc.eoloplanner.server.repository.EoloPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class SampleDatabaseLoader {

	@Autowired 
	private EoloPlantRepository posts;
	
	@PostConstruct
	public void init() {
		posts.save(new EoloPlant("Madrid", "madrid-sunny-flat"));
	}
}

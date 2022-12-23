package es.codeurjc.eoloplanner.server.service;

import es.codeurjc.eoloplanner.server.model.EoloPlant;
import es.codeurjc.eoloplanner.server.repository.EoloPlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class EoloPlantService {

    @Autowired
    private EoloPlantRepository eoloPlants;

    @Autowired
    private EoloPlantCreatorService eoloPlantCreator;

    public Collection<EoloPlant> findAll() {
        return eoloPlants.findAll();
    }

    public Optional<EoloPlant> findById(long id) {
        return eoloPlants.findById(id);
    }

    public EoloPlant createEoloplant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        EoloPlant eoloPlant = eoloPlantCreator.createEoloPlant(eoloPlantCreationRequest);

        eoloPlants.save(eoloPlant);

        return eoloPlant;
    }

    public EoloPlant deleteById(long id) {

        EoloPlant eoloPlant = eoloPlants.findById(id).orElseThrow();

        eoloPlants.deleteById(id);

        return eoloPlant;
    }
}

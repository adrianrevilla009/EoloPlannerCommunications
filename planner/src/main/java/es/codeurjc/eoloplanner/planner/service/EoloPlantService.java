package es.codeurjc.eoloplanner.planner.service;

import es.codeurjc.eoloplanner.planner.model.EoloPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class EoloPlantService {

    @Autowired
    private EoloPlantCreatorService eoloPlantCreator;

    public EoloPlant createEoloplant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        EoloPlant eoloPlant = eoloPlantCreator.createEoloPlant(eoloPlantCreationRequest);

        return eoloPlant;
    }

}

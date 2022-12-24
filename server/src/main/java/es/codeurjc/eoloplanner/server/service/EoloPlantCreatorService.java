package es.codeurjc.eoloplanner.server.service;

import es.codeurjc.eoloplanner.server.model.EoloPlant;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class EoloPlantCreatorService {

    public EoloPlant createEoloPlant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        String city = eoloPlantCreationRequest.getCity();

        StringBuffer planningCreation = new StringBuffer(city);

        String planning = planningCreation.toString();

        planning = planning.matches("^[A-Ma-m].*") ?
                planning.toLowerCase() :
                planning.toUpperCase();

        simulateProcessWaiting();

        return new EoloPlant(eoloPlantCreationRequest.getCity(), planning);
    }

    private void simulateProcessWaiting() {
        try {
            Thread.sleep(1000 + new Random().nextInt(2000));
        } catch (InterruptedException e) {}
    }
}

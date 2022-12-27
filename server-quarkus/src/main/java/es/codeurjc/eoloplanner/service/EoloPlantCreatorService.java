package es.codeurjc.eoloplanner.service;

import es.codeurjc.eoloplanner.model.EoloPlant;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@ApplicationScoped
public class EoloPlantCreatorService {

    public EoloPlant createEoloPlant(EoloPlant eoloPlantCreationRequest) {

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

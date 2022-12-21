package es.codeurjc.eoloplanner.planner.service;

import es.codeurjc.eoloplanner.planner.client.TopoClient;
import es.codeurjc.eoloplanner.planner.client.WeatherClient;
import es.codeurjc.eoloplanner.planner.model.EoloPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class EoloPlantCreatorService {

    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private TopoClient topoClient;

    public EoloPlant createEoloPlant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        String city = eoloPlantCreationRequest.getCity();

        StringBuffer planningCreation = new StringBuffer(city);

        CompletableFuture<Void> landscape = topoClient.getLandscape(city).thenAccept(l -> {
            System.out.println("L");
            planningCreation.append("-");
            planningCreation.append(l);
        });

        CompletableFuture<Void> weather = weatherClient.getWeather(city).thenAccept(w -> {
            System.out.println("W");
            planningCreation.append("-");
            planningCreation.append(w);
        });;

        CompletableFuture.allOf(weather, landscape).get();

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

package es.codeurjc.eoloplanner.planner.service;

import es.codeurjc.eoloplanner.planner.client.TopoClient;
import es.codeurjc.eoloplanner.planner.client.WeatherClient;
import es.codeurjc.eoloplanner.planner.model.EoloPlant;
import es.codeurjc.eoloplanner.planner.model.EoloPlantResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Configuration
public class EoloPlantCreatorService {

    private WeatherClient weatherClient;

    private TopoClient topoClient;

    Logger logger = LoggerFactory.getLogger(EoloPlantCreatorService.class);

    private final Source source;

    public EoloPlantCreatorService(WeatherClient weatherClient, TopoClient topoClient, Source source) {
        this.weatherClient = weatherClient;
        this.topoClient = topoClient;
        this.source = source;
    }

    public EoloPlant createEoloPlant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        this.sendEoloplantCreationProgressNotification(eoloPlantCreationRequest, 25, false);

        String city = eoloPlantCreationRequest.getCity();

        StringBuffer planningCreation = new StringBuffer(city);

        AtomicReference<Boolean> firstServiceReached = new AtomicReference<>(false);

        CompletableFuture<Void> weather = weatherClient.getWeather(city).thenAccept(w -> {
            System.out.println("W");
            planningCreation.append("-");
            planningCreation.append(w);
            this.setProgressDependingOnFirstCompletedPromise(eoloPlantCreationRequest, firstServiceReached.get());
            firstServiceReached.set(true);
        });


        CompletableFuture<Void> landscape = topoClient.getLandscape(city).thenAccept(l -> {
            System.out.println("L");
            planningCreation.append("-");
            planningCreation.append(l);
            this.setProgressDependingOnFirstCompletedPromise(eoloPlantCreationRequest, firstServiceReached.get());
            firstServiceReached.set(true);
        });

        CompletableFuture.allOf(weather, landscape).get();

        String planning = planningCreation.toString();

        planning = planning.matches("^[A-Ma-m].*") ?
                planning.toLowerCase() :
                planning.toUpperCase();

        EoloPlant createdEoloPlant = new EoloPlant(eoloPlantCreationRequest.getId(), eoloPlantCreationRequest.getCity(), planning);

        this.sendEoloplantCreationProgressNotification(createdEoloPlant, 100, true);

        simulateProcessWaiting();

        return createdEoloPlant;
    }

    private void simulateProcessWaiting() {
        try {
            Thread.sleep(1000 + new Random().nextInt(2000));
        } catch (InterruptedException e) {}
    }

    public void setProgressDependingOnFirstCompletedPromise(EoloPlant eoloPlantCreationRequest, Boolean firstServiceReached) {
        if (!firstServiceReached) {
            this.sendEoloplantCreationProgressNotification(eoloPlantCreationRequest, 50, false);
        } else {
            this.sendEoloplantCreationProgressNotification(eoloPlantCreationRequest, 75, false);
        }
    }

    public void sendEoloplantCreationProgressNotification(EoloPlant eoloPlant, Integer progress, Boolean completed) {
        this.logger.info("Sending " + progress + "% progress event");
        EoloPlantResponse eoloPlantResponse = new EoloPlantResponse(eoloPlant.getId(), eoloPlant.getCity(),
                eoloPlant.getPlanning(), progress, completed);
        source.output().send(new GenericMessage<>(eoloPlantResponse));
    }
}

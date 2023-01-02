package es.codeurjc.eoloplanner.resource;

import es.codeurjc.eoloplanner.WebSocketHandler;
import es.codeurjc.eoloplanner.model.EoloPlant;
import es.codeurjc.eoloplanner.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.service.EoloPlantResponseService;
import es.codeurjc.eoloplanner.service.EoloPlantService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.graphql.*;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@GraphQLApi
public class EoloPlantResource {

    @Inject
    private EoloPlantService eoloPlantService;

    @Inject
    private WebSocketHandler webSocketHandler;

    @Inject
    private EoloPlantResponseService eoloPlantResponseService;

    Logger LOG = Logger.getLogger(String.valueOf(EoloPlantResource.class));

    @Query("eoloPlants")
    public Collection<EoloPlant> eoloPlants() {
        return eoloPlantService.findAll();
    }

    @Query("eoloPlant")
    public Optional<EoloPlant> eoloPlant(@Id long id) {
        return eoloPlantService.findById(id);
    }

    @Mutation("createEoloPlant")
    public EoloPlant createEoloPlant(EoloPlant eoloPlant) throws ExecutionException, InterruptedException, IOException {
        EoloPlant savedEoloPlant = eoloPlantService.createEoloplant(eoloPlant);

        this.sendWebSocketResponse(new EoloPlantResponse(savedEoloPlant.getId(), eoloPlant.getCity(), eoloPlant.getPlanning(), 0, false));

        return savedEoloPlant;
    }

    @Mutation("deleteEoloPlant")
    public EoloPlant deleteEoloPlant(@Id long id) {
        return eoloPlantService.deleteById(id);
    }

    private void sendWebSocketResponse(EoloPlantResponse eoloPlantResponse) throws IOException, InterruptedException {
        for (Map.Entry<String, Session> sessionEntry :this.webSocketHandler.getSessionMap().entrySet()) {
            this.webSocketHandler.onMessage(eoloPlantResponse.toJson(), sessionEntry.getKey());
        }
    }

    @Incoming("eoloplantCreationProgressNotifications")
    @Blocking
    public void handle(JsonObject p) throws InterruptedException, IOException {
        EoloPlantResponse eoloPlantResponse = p.mapTo(EoloPlantResponse.class);
        this.LOG.info("Receiving " + eoloPlantResponse.getProgress() + "% progress event");

        this.eoloPlantResponseService.save(eoloPlantResponse);

        this.sendWebSocketResponse(eoloPlantResponse);
    }
}
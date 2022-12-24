package es.codeurjc.eoloplanner.server.service;

import es.codeurjc.eoloplanner.server.model.EoloPlant;
import es.codeurjc.eoloplanner.server.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.server.repository.EoloPlantRepository;
import es.codeurjc.eoloplanner.server.ws.WebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

@Service
@Configuration
public class EoloPlantService {

    private EoloPlantRepository eoloPlants;

    private EoloPlantCreatorService eoloPlantCreator;

    private EoloPlantResponseService eoloPlantResponseService;

    private final Source source;

    Logger logger = LoggerFactory.getLogger(EoloPlantService.class);

    public EoloPlantService(Source source, EoloPlantRepository eoloPlantRepository,
                            EoloPlantCreatorService eoloPlantCreator,
                            EoloPlantResponseService eoloPlantResponseService) {
        this.source = source;
        this.eoloPlants = eoloPlantRepository;
        this.eoloPlantCreator = eoloPlantCreator;
        this.eoloPlantResponseService = eoloPlantResponseService;
    }

    public Collection<EoloPlant> findAll() {
        return eoloPlants.findAll();
    }

    public Optional<EoloPlant> findById(long id) {
        return eoloPlants.findById(id);
    }

    public EoloPlant createEoloplant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        EoloPlant eoloPlant = eoloPlantCreator.createEoloPlant(eoloPlantCreationRequest);

        EoloPlant savedEoloPlant = eoloPlants.save(eoloPlant);
        this.eoloPlantResponseService.save(new EoloPlantResponse(eoloPlant.getId(), eoloPlant.getCity(), eoloPlant.getPlanning(), 0, false));

        this.sendEoloplantCreationRequest(savedEoloPlant);

        return eoloPlant;
    }

    public EoloPlant deleteById(long id) {

        EoloPlant eoloPlant = eoloPlants.findById(id).orElseThrow();

        eoloPlants.deleteById(id);

        return eoloPlant;
    }

    public void sendEoloplantCreationRequest(EoloPlant savedEoloPlant) {
        this.logger.info("Sending create event");
        source.output().send(new GenericMessage<>(savedEoloPlant));
    }
}

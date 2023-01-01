package es.codeurjc.eoloplanner.service;

import es.codeurjc.eoloplanner.model.EoloPlant;
import es.codeurjc.eoloplanner.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.repository.EoloPlantRepository;
import es.codeurjc.eoloplanner.resource.EoloPlantResource;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@ApplicationScoped
public class EoloPlantService {
    @Inject
    private EoloPlantRepository eoloPlantRepository;

    @Inject
    private EoloPlantCreatorService eoloPlantCreatorService;

    @Inject
    private EoloPlantResponseService eoloPlantResponseService;

    @Channel("eoloplantCreationRequests")
    Emitter<EoloPlant> emitter;

    Logger LOG = Logger.getLogger(String.valueOf(EoloPlantService.class));

    public Collection<EoloPlant> findAll() {
        return this.eoloPlantRepository.listAll();
    }

    public Optional<EoloPlant> findById(long id) {
        return this.eoloPlantRepository.findByIdOptional(id);
    }

    @Transactional
    public EoloPlant createEoloplant(EoloPlant eoloPlantCreationRequest) throws ExecutionException, InterruptedException {

        EoloPlant eoloPlant = this.eoloPlantCreatorService.createEoloPlant(eoloPlantCreationRequest);

        this.eoloPlantRepository.persist(eoloPlant);

        this.eoloPlantResponseService.save(new EoloPlantResponse(eoloPlant.getId(), eoloPlant.getCity(), eoloPlant.getPlanning(), 0, false));

        this.sendEoloplantCreationRequest(eoloPlant);

        return eoloPlant;
    }

    @Transactional
    public EoloPlant deleteById(long id) {

        EoloPlant eoloPlant = this.eoloPlantRepository.findByIdOptional(id).orElseThrow();

        this.eoloPlantRepository.deleteById(id);

        return eoloPlant;
    }

    public void sendEoloplantCreationRequest(EoloPlant savedEoloPlant) {
        this.LOG.info("Sending create event");
        emitter.send(savedEoloPlant);
    }
}

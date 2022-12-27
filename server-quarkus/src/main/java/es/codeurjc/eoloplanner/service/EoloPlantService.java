package es.codeurjc.eoloplanner.service;

import es.codeurjc.eoloplanner.model.EoloPlant;
import es.codeurjc.eoloplanner.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.repository.EoloPlantRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class EoloPlantService {
    @Inject
    private EoloPlantRepository eoloPlantRepository;

    @Inject
    private EoloPlantCreatorService eoloPlantCreatorService;

    @Inject
    private EoloPlantResponseService eoloPlantResponseService;

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

        // this.sendEoloplantCreationRequest(savedEoloPlant);

        return eoloPlant;
    }

    @Transactional
    public EoloPlant deleteById(long id) {

        EoloPlant eoloPlant = this.eoloPlantRepository.findByIdOptional(id).orElseThrow();

        this.eoloPlantRepository.deleteById(id);

        return eoloPlant;
    }
}

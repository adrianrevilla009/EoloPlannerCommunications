package es.codeurjc.eoloplanner.service;

import es.codeurjc.eoloplanner.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.repository.EoloPlantResponseRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class EoloPlantResponseService {
    @Inject
    private EoloPlantResponseRepository eoloPlantResponseRepository;

    @Transactional
    public void save(EoloPlantResponse eoloPlantResponse) {
        Long eoloPlantId = eoloPlantResponse.getId();
        eoloPlantResponse.setId(null);
        this.eoloPlantResponseRepository.persist(eoloPlantResponse);
        eoloPlantResponse.setId(eoloPlantId);
    }
}

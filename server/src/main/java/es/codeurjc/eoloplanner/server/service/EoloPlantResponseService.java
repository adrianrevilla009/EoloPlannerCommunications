package es.codeurjc.eoloplanner.server.service;

import es.codeurjc.eoloplanner.server.model.EoloPlantResponse;
import es.codeurjc.eoloplanner.server.repository.EoloPlantResponseRepository;
import org.springframework.stereotype.Service;

@Service
public class EoloPlantResponseService {
    private EoloPlantResponseRepository eoloPlantResponseRepository;

    public EoloPlantResponseService(EoloPlantResponseRepository eoloPlantResponseRepository) {
        this.eoloPlantResponseRepository = eoloPlantResponseRepository;
    }

    public void save(EoloPlantResponse eoloPlantResponse) {
        Long eoloPlantId = eoloPlantResponse.getId();
        eoloPlantResponse.setId(null);
        this.eoloPlantResponseRepository.save(eoloPlantResponse);
        eoloPlantResponse.setId(eoloPlantId);
    }
}

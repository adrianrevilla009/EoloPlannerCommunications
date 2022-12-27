package es.codeurjc.eoloplanner.repository;

import es.codeurjc.eoloplanner.model.EoloPlantResponse;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EoloPlantResponseRepository implements PanacheRepository<EoloPlantResponse> {
}

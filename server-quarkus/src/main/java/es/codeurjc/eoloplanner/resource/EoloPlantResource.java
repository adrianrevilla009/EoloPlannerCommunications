package es.codeurjc.eoloplanner.resource;

import es.codeurjc.eoloplanner.model.EoloPlant;
import es.codeurjc.eoloplanner.service.EoloPlantService;
import org.eclipse.microprofile.graphql.*;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@GraphQLApi
public class EoloPlantResource {

    @Inject
    private EoloPlantService eoloPlantService;

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

        // this.sendWebSocketResponse(new EoloPlantResponse(savedEoloPlant.getId(), eoloPlant.getCity(), eoloPlant.getPlanning(), 0, false));

        return savedEoloPlant;
    }

    @Mutation("deleteEoloPlant")
    public EoloPlant deleteEoloPlant(@Id long id) {
        return eoloPlantService.deleteById(id);
    }
}
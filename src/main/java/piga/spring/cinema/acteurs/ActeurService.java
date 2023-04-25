package piga.spring.cinema.acteurs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import piga.spring.cinema.acteurs.dto.ActeurCompletDto;
import piga.spring.cinema.acteurs.dto.ActeurSansFilmDto;

import java.util.List;

@Service
public class ActeurService {

    private final ActeurRepository repository;
    private final ObjectMapper mapper;

    public ActeurService(ActeurRepository repository, ObjectMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public ActeurCompletDto save(Acteur entity) {
        Acteur entitySauvegarder = repository.save(entity);
        return mapper.convertValue(entitySauvegarder, ActeurCompletDto.class);
    }

    public ActeurCompletDto findById(Integer integer) {
        Acteur entity = repository.findById(integer).orElseThrow();
        return mapper.convertValue(entity, ActeurCompletDto.class);
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    public List<ActeurSansFilmDto> findAll() {
        List<Acteur> entities = repository.findAll();
        return entities.stream().map(acteur -> mapper.convertValue(acteur, ActeurSansFilmDto.class)).toList();
    }

    public Acteur findOrInsert(Acteur acteur){
        return this.repository.findById(acteur.getId()).orElseGet(()->this.repository.save(acteur));
    }

}

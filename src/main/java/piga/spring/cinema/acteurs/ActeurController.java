package piga.spring.cinema.acteurs;



import piga.spring.cinema.acteurs.dto.ActeurCompletDto;
import piga.spring.cinema.acteurs.dto.ActeurSansFilmDto;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acteurs")
@CrossOrigin
public class ActeurController {

    private final ActeurService service;

    public ActeurController(ActeurService service) {
        this.service = service;
    }

    @GetMapping
    public List<ActeurSansFilmDto> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public ActeurCompletDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public ActeurCompletDto save(@RequestBody Acteur entity) {
        return service.save(entity);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        service.deleteById(id);
    }

}

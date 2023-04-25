package piga.spring.cinema.salles;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("salles")
@CrossOrigin
public class SalleController {

    private final SalleService service;

    public SalleController(SalleService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Salle> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Salle findById(Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public Salle save(@RequestBody Salle salle) {
        return service.save(salle);
    }

    @DeleteMapping("{id}")
    public void deleteById(Integer id) {
        service.deleteById(id);
    }

    /**
     * <h2>Retourne la liste des salles disponibles a une date et une heure donnees.</h2>
     *
     * @param date la date de la séance
     */
    @GetMapping("seances/date/{date}")
    public Iterable<Salle> findSallesDisponibles(@PathVariable LocalDateTime date) {
        return service.findSallesDisponibles(date);
    }

}

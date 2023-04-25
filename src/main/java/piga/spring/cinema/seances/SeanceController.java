package piga.spring.cinema.seances;


import piga.spring.cinema.serviceExterieur.SeanceSalleService;
import piga.spring.cinema.serviceExterieur.SeanceTicketService;
import piga.spring.cinema.tickets.Ticket;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("seances")
public class SeanceController {

    private final SeanceService service;

    private final SeanceTicketService seanceTicketService;

    private final SeanceSalleService seanceSalleService;

    public SeanceController(SeanceService service, SeanceTicketService seanceTicketService, SeanceSalleService seanceSalleService) {
        this.service = service;
        this.seanceTicketService = seanceTicketService;
        this.seanceSalleService = seanceSalleService;
    }

    @GetMapping
    public List<Seance> findAll() {
        return service.findAll();
    }

    @GetMapping("{id}")
    public Seance findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping("/")
    public Seance save(@RequestBody Seance entityNotSaved) {
        System.out.println(entityNotSaved); // JSON
        if (entityNotSaved.getSalle() == null)  throw new SeanceBadRequestException("Il faut une salle");
        if (entityNotSaved.getFilm() == null)  throw new SeanceBadRequestException("Il faut un film");
        return seanceSalleService.save(entityNotSaved);

    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        service.deleteById(id);
    }

    @GetMapping("{id}/tickets")
    public List<Ticket> findTickets(@PathVariable Integer id) {
        return seanceTicketService.findTickets(id);
    }


}

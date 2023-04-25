package piga.spring.cinema.tickets;

import piga.spring.cinema.seances.Seance;
import piga.spring.cinema.seances.SeanceService;
import org.springframework.stereotype.Service;
import piga.spring.cinema.serviceExterieur.SeanceSalleService;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repository;
    private final SeanceSalleService seanceSalleService;
    private final SeanceService seanceService;

    public TicketService(TicketRepository repository, SeanceSalleService seanceSalleService, SeanceService seanceService) {
        this.repository = repository;
        this.seanceSalleService = seanceSalleService;
        this.seanceService = seanceService;
    }

    /**
     * Sauvegarde un ticket et decremente le nombre de place disponible de la seance
     * @param ticket le ticket a sauvegarder
     * @return le ticket sauvegarder avec son id
     */
    public Ticket save(Ticket ticket) {
        Seance seance = seanceService.findById(ticket.getSeance().getId());
        if (seance.getNombrePlace() - ticket.getNombrePlace() <= 0) {
            throw new PlusDePlaceException(ticket.getNombrePlace(), seance.getNombrePlace());
        }
        Ticket ticketSauvegarder = repository.save(ticket);
        seance.setNombrePlace(seance.getNombrePlace() - ticket.getNombrePlace());
        seanceSalleService.save(seance);
        return ticketSauvegarder;
    }

    public Ticket findById(Integer integer) {
        return repository.findById(integer).orElseThrow();
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    public Iterable<Ticket> findAll() {
        return repository.findAll();
    }



    public List<Ticket> findAllBySeanceId(Integer id) {
        return repository.findAllBySeanceId(id);
    }
}

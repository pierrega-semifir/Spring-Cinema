package piga.spring.cinema.seances;

import org.springframework.context.annotation.Lazy;
import piga.spring.cinema.salles.Salle;
import piga.spring.cinema.salles.SalleService;
import piga.spring.cinema.tickets.Ticket;
import piga.spring.cinema.tickets.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeanceService {

    private final SeanceRepository repository;

    public SeanceService(SeanceRepository repository) {
        this.repository = repository;
    }

    public List<Seance> findAll() {
        return repository.findAll();
    }


    public Seance findById(Integer integer) {
        return repository.findById(integer).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    /**
     * <h2>Retourne la liste des seances a une date donnée</h2>
     * @param date la date a laquelle on veut les seances
     */
    public List<Seance> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }


}

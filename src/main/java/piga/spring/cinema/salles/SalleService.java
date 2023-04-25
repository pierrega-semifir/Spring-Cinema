package piga.spring.cinema.salles;

import org.springframework.context.annotation.Lazy;
import piga.spring.cinema.seances.Seance;
import piga.spring.cinema.seances.SeanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalleService {

    private final SalleRepository repository;
    private final SeanceService seanceService;

    public SalleService(SalleRepository repository, SeanceService seanceService) {
        this.repository = repository;
        this.seanceService = seanceService;
    }

    public Salle save(Salle entity) {
        return repository.save(entity);
    }

    public Salle findById(Integer integer) {
        return repository.findById(integer).orElseThrow();
    }

    public void deleteById(Integer integer) {
        repository.deleteById(integer);
    }

    public Iterable<Salle> findAll() {
        return repository.findAll();
    }

    public Iterable<Salle> findSallesDisponibles(LocalDateTime date) {
        List<Seance> seances = seanceService.findByDate(date.toLocalDate());
        seances = seances.stream().filter(seance -> {
            LocalDateTime debut = seance.getDate();
            LocalDateTime fin = seance.getDate().plusMinutes(seance.getFilm().getDuree());
            return debut.isAfter(date) || fin.isBefore(date);
        }).toList();
        return repository.findAllById(seances.stream().map(Seance::getSalle).map(Salle::getId).toList());
    }
}

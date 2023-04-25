package piga.spring.cinema.serviceExterieur;

import org.springframework.stereotype.Service;
import piga.spring.cinema.salles.Salle;
import piga.spring.cinema.salles.SalleService;
import piga.spring.cinema.seances.Seance;
import piga.spring.cinema.seances.SeanceRepository;
@Service
public class SeanceSalleService {

    private final SeanceRepository repository;
    private final SalleService salleService;

    public SeanceSalleService(SeanceRepository repository, SalleService salleService) {
        this.repository = repository;
        this.salleService = salleService;
    }

    /**
     * Sauvegarde une seance et defini le nombre de place disponible en fonction de la capacite de la salle
     * @param entity la seance a sauvegarder
     * @return la seance sauvegarder avec son id et le nombre de place disponible
     */
    public Seance save(Seance entity) {
        System.out.println(entity);
        Salle salle = salleService.findById(entity.getSalle().getId());
        entity.setNombrePlace(salle.getCapacite());
        return repository.save(entity);
    }
}

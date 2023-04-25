package piga.spring.cinema.films;

import piga.spring.cinema.acteurs.Acteur;
import piga.spring.cinema.acteurs.ActeurService;
import piga.spring.cinema.exceptions.NotFoundException;
import piga.spring.cinema.realisateurs.Realisateur;
import piga.spring.cinema.seances.Seance;
import piga.spring.cinema.seances.SeanceService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe Service pour les {@link Film}s.
 *
 * Elle permet la gestion des films et de leurs traitements métiers.
 * Elle ne gère ni les requêtes HTTP, ni la connexion à la base de données.
 *
 */
@Service
public class FilmService {

    /**
     * Le repository pour les films. <br>
     * C'est grâce à lui que l'on peut accéder à la base de données.
     */
    private final FilmJpaRepository jpaRepository;

    /**
     * Le service pour les acteurs. <br>
     * Utile pour la gestion des acteurs.
     */
    private final ActeurService acteurService;

    /**
     * Le service pour les séances. <br>
     */
    private final SeanceService seanceService;

    /**
     * Constructeur du service pour les films.
     *
     * @param jpaRepository le repository pour les films.
     * @param acteurService le service pour les acteurs.
     * @param seanceService le service pour les séances.
     */
    public FilmService(FilmJpaRepository jpaRepository, ActeurService acteurService, SeanceService seanceService) {
        this.jpaRepository = jpaRepository;
        this.acteurService = acteurService;
        this.seanceService = seanceService;
    }

    /**
     * Sauvegarde un film dans la base de données.
     * Si le film posséde aucun id, alors sauvegarde le film et lui donne un id.
     * Sinon remplace le film portant l'id dans la base de données.
     * @param film a sauvegarder
     * @return film sauvegarder.
     */
    public Film save(Film film){
        return jpaRepository.save(film);

    }

    /**
     * Retourne la liste de l'ensemble des films.
     * @return liste de l'ensemble des films.
     */
    public List<Film> findAll(){
        /*
        En JS
        let entities = ...
        return entities.map(entity => mapper.convertValue(entity, FilmReduitDto.class))
         */
        return this.jpaRepository.findAll();
    }

    /**
     * Retourne le film portant l'id.
     * @param id du film a rechercher
     * @return le film rechercher
     * @throws ResponseStatusException si aucun ne porte cet id dans la base de données,
     *      alors retourne cette exception avec le status 404 NOT_FOUND
     */
    public Film findById(Integer id) {
        return jpaRepository.findById(id).orElseThrow(()->new NotFoundException("Aucun film ne porte l'id "+id));
    }

    /**
     * Supprime le film portant l'id dans la base de données. Ne fait rien si aucun film ne porte cette id.
     * @param id du film a supprimer.
     */
    public void deleteById(Integer id) {
        jpaRepository.deleteById(id);
    }

    /**
     * Retourne la liste des {@link Film}s ou {@link Film#titre} contient le mot clef.
     * @param titre a rechercher
     * @return liste des {@link Film}s
     */
    public List<Film> findByTitreContaining(String titre){
        return jpaRepository.findByTitreContaining(titre);
    }

    /**
     * Ajoute un {@link Acteur} a un {@link Film} en fonction de l'id du film et l'id de l'acteur
     * @param id du {@link Film}
     * @param idActeur id de l'{@link Acteur}
     */
    public void addActeurById(Integer id, Integer idActeur) {
        Acteur acteur = new Acteur();
        acteur.setId(idActeur);
        addActeur(id, acteur);
    }

    /**
     * Ajoute un {@link Acteur} au {@link Film} en fonction de l'id du film et de l'acteur.
     * @param id du film
     * @param acteur a ajouter
     */
    public void addActeur(Integer id, Acteur acteur) {
        Film film = jpaRepository.findById(id).orElseThrow();
        Acteur acteurAAjouter = this.acteurService.findOrInsert(acteur);
        System.out.println("Acteur a ajouter : " + acteurAAjouter.getId());
        System.out.println("Film" + film.getId());
        film.getActeurs().add(acteurAAjouter);
        jpaRepository.save(film);
    }

    /**
     * Supprime un {@link Acteur} du {@link Film} en fonction de l'id du film et de l'acteur.
     * @param id du film
     * @param idActeur id de l'{@link Acteur}
     */
    public void deleteActeurById(Integer id, Integer idActeur) {
        Film film = jpaRepository.findById(id).orElseThrow();
        film.getActeurs().removeIf(acteur -> acteur.getId().equals(idActeur));
        jpaRepository.save(film);
    }

    /**
     * Supprime un {@link fr.kira.formation.spring.cinema.realisateurs.Realisateur} d'un {@link Film} en fonction de leurs ids;
     * @param id du film
     * @param idRealisateur du realisateur
     */
    public void deleteRealisateurById(Integer id, Integer idRealisateur) {
        Film film = jpaRepository.findById(id).orElseThrow();
        film.getRealisateurs().removeIf(realisateur -> realisateur.getId().equals(idRealisateur));
        jpaRepository.save(film);
    }

    /**
     * Ajoute un {@link Realisateur} d'un {@link Film} en fonction de leurs ids;
     * @param id du film
     * @param idRealisateur du realisateur
     */
    public void addRealisateurById(Integer id, Integer idRealisateur) {
        Realisateur realisateur = new Realisateur();
        realisateur.setId(idRealisateur);
        Film film = jpaRepository.findById(id).orElseThrow();
        film.getRealisateurs().add(realisateur);
        jpaRepository.save(film);
    }

    /**
     * Retourne la liste des {@link Film} possédant une seance a une date donnée.
     * @param date date a rechercher
     * @return liste des {@link Film} possédant une seance a une date donnée.
     */
    public List<Film> findBySeanceDate(LocalDate date) {
        List<Seance> seances = seanceService.findByDate(date);
        return seances.stream().map(Seance::getFilm).toList();
    }
}

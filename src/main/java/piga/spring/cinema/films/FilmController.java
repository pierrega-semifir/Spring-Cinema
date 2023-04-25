package piga.spring.cinema.films;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Lazy;
import piga.spring.cinema.acteurs.Acteur;
import piga.spring.cinema.exceptions.BadRequestException;
import piga.spring.cinema.films.dto.FilmCompletDto;
import piga.spring.cinema.films.dto.FilmReduitDto;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("films")
@CrossOrigin // evite les problèmes les CORS
public class FilmController {

    private final FilmService service;
    private final ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(FilmController.class);


    public FilmController(FilmService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * <h2>Sauvegarde ou remplace un {@link Film} dans la base de données.</h2>
     * <ul>
     *     <li>
     *         Si le film posséde aucun id, alors sauvegarde le film et lui donne un id.
     *     </li>
     *     <li>
     *         Sinon remplace le film portant l'id dans la base de données.
     *     </li>
     * </ul>
     * <p>
     *     <b>Attention!</b> Pour remplacer un film, il est préférable de passer par la méthode {@link #update(Film)}.
     * </p>
     * @param film a sauvegarder
     * @return film sauvegarder.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmCompletDto save(@RequestBody Film film) {
        try {
            Film entity = service.save(film);
            return mapper.convertValue(entity, FilmCompletDto.class);
        } catch (PropertyValueException | DataIntegrityViolationException e) {
            logger.warn("Le film doit posséder un titre et une date de sortie. "+film);
            throw new BadRequestException("Le film doit posséder un titre et une date de sortie.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            throw new RuntimeException("Le film n'a pas pu être sauvegardé.", e);
        }

    }

    /**
     * <h2>Retourne la liste de tous les films.</h2>
     *
     * @return la liste de tous les films.
     */
    @GetMapping
    public List<FilmReduitDto> findAll() {
        return service.findAll()
                .stream()
                    .map(film -> mapper.convertValue(film, FilmReduitDto.class))
                .toList();
    }

    /**
     * <h2>Retourne le film correspondant à l'id.</h2>
     *
     * @param id l'id du film à retourner.
     * @return le film correspondant à l'id.
     */
    @GetMapping("{id}")
    public FilmCompletDto findById(@PathVariable Integer id) {
        Film entity = service.findById(id);
        return mapper.convertValue(entity, FilmCompletDto.class);
    }

    /**
     * <h2>Supprime le film correspondant à l'id.</h2>
     *
     * @param id l'id du film à supprimer.
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        service.deleteById(id);
    }

    /**
     * <h2>Sauvegarde ou remplace un {@link Film} dans la base de données.</h2>
     * <ul>
     *     <li>
     *         Si le film posséde aucun id, alors sauvegarde le film et lui donne un id.
     *     </li>
     *     <li>
     *         Sinon remplace le film portant l'id dans la base de données.
     *     </li>
     * </ul>
     * @param film a sauvegarder
     * @return film sauvegarder.
     */
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmCompletDto update(@RequestBody Film film){
        return this.save(film);
    }

    @GetMapping("titre/{titre}")
    public List<FilmReduitDto> findByTitre(@PathVariable String titre){
        List<Film> entities = this.service.findByTitreContaining(titre);
        return entities.stream().map(film -> mapper.convertValue(film, FilmReduitDto.class)).toList();
    }

    /**
     * <h2>Ajoute un acteur a un film en utilisant l'id de l'acteur et l'id du film.</h2>
     * <p>C'est la version la qui utilise l'id de l'acteur. Vous pouvez aussi utiliser l'acteur au complet avec la methode
     * {@link FilmController#addActeur(Integer, Acteur)}</p>
     *
     * <p>
     *     Pour utiliser cette methode, vous devez au préalable avoir ajouté un acteur avec la methode
     *      {@link fr.kira.formation.spring.cinema.acteurs.ActeurController#save(Acteur)}
     * </p>
     *
     * <p>Pour appeler cette methode, vous devez utiliser une requete HTTP de type POST sur l'url suivante:
     *    <code>/films/{idFilm}/acteurs/{idActeur}</code>
     * </p>
     *
     * @param id du film
     * @param idActeur id de l'acteur
     *
     */
    @PostMapping("{id}/acteurs/{idActeur}")
    public void addActeurById(@PathVariable Integer id, @PathVariable Integer idActeur){
        this.service.addActeurById(id, idActeur);
    }
    // Ou
    /**
     * <h2>Ajoute un acteur a un film en utilisant l'acteur et l'id du film.</h2>
     * <p>C'est la version la qui utilise l'acteur au complet. Vous pouvez aussi utiliser l'id de l'acteur avec la methode
     *    {@link FilmController#addActeurById(Integer, Integer)}
     * </p>
     *
     * <p>
     *     Pour utiliser cette methode, vous devez au préalable avoir ajouté un acteur avec la methode
     *     {@link fr.kira.formation.spring.cinema.acteurs.ActeurController#save(Acteur)}
     * </p>
     * <p>
     *     Pour appeler cette methode, vous devez utiliser une requete HTTP de type POST sur l'url suivante:
     *     <code>/films/{idFilm}/acteurs</code>
     * </p>
     * @param id du film ou ajouter l'acteur
     * @param acteur a ajouter
     */
    @PostMapping("{id}/acteurs")

    public void addActeur(@PathVariable Integer id, @RequestBody Acteur acteur){
        this.service.addActeur(id, acteur);
    }

    /**
     * <h2>Supprime un acteur d'un film en utilisant l'id de l'acteur et l'id du film.</h2>
     * <p>
     *     Pour appeler cette methode, vous devez utiliser une requete HTTP de type DELETE sur l'url suivante:
     *     <code>/films/{idFilm}/acteurs/{idActeur}</code>
     * </p>
     * @param id du film
     * @param idActeur id de l'acteur
     */
    @DeleteMapping("{id}/acteurs/{idActeur}")
    public void deleteActeur(@PathVariable Integer id, @PathVariable Integer idActeur){
        this.service.deleteActeurById(id, idActeur);
    }

    /**
     * <h2>Ajoute un {@link piga.spring.cinema.realisateurs.Realisateur} d'un {@link Film} en fonction de leurs ids</h2>
     * @param id du film
     * @param idRealisateur du realisateur
     */
    @PostMapping("{id}/realisateurs/{idRealisateur}")
    public void addRealisateur(@PathVariable Integer id, @PathVariable Integer idRealisateur){
        this.service.addRealisateurById(id, idRealisateur);
    }

    /**
     * <h2>Supprime un {@link fr.kira.formation.spring.cinema.realisateurs.Realisateur} d'un {@link Film} en fonction de leurs ids</h2>
     * @param id du film
     * @param idRealisateur du realisateur
     */
    @DeleteMapping("{id}/realisateurs/{idRealisateur}")
    public void deleteRealisateur(@PathVariable Integer id, @PathVariable Integer idRealisateur){
        this.service.deleteRealisateurById(id, idRealisateur);
    }

    /**
     * <h2>
     *     Retourne la liste des films possédant au moins une seance a une date données.
     * </h2>
     *
     * @param date date a laquelle il faut que le film ait une seance.
     */
    @GetMapping("seances/date/{date}")
    public List<FilmReduitDto> findBySeanceDate(@PathVariable LocalDate date){
        List<Film> entities = this.service.findBySeanceDate(date);
        return entities.stream().map(film -> mapper.convertValue(film, FilmReduitDto.class)).toList();
    }

}

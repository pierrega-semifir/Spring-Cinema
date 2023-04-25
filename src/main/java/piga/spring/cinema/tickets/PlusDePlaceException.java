package piga.spring.cinema.tickets;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class PlusDePlaceException extends RuntimeException {
    public PlusDePlaceException(int placesDemandees, int placesDisponibles) {
        super("Il n'y a plus de place pour le film, vous demendez " + placesDemandees + " alors qu'il reste "+placesDisponibles);
    }
}
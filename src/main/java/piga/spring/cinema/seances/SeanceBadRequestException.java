package piga.spring.cinema.seances;

import piga.spring.cinema.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SeanceBadRequestException extends BadRequestException {

    public SeanceBadRequestException(String message) {
        super("Le format de la seance n'est pas valide: " + message);
    }
}

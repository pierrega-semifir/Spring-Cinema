package piga.spring.cinema.tickets;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TicketBadRequestException extends RuntimeException {
    public TicketBadRequestException(String message) {
        super("Le format du ticket n'est pas valide: " + message);
    }
}

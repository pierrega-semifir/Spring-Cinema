package piga.spring.cinema.serviceExterieur;


import org.springframework.stereotype.Service;
import piga.spring.cinema.seances.SeanceRepository;
import piga.spring.cinema.tickets.Ticket;
import piga.spring.cinema.tickets.TicketService;

import java.util.List;
@Service
public class SeanceTicketService {

    //private final SeanceRepository repository;
    private final TicketService ticketService;

    public SeanceTicketService( TicketService ticketService) {
        //this.repository = repository;
        this.ticketService = ticketService;
    }

    public List<Ticket> findTickets(Integer id) {
        return ticketService.findAllBySeanceId(id);
    }


}

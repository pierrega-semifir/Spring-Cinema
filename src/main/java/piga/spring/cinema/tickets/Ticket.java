package piga.spring.cinema.tickets;

import piga.spring.cinema.seances.Seance;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String nomClient;

    private int nombrePlace;

    @ManyToOne
    @JoinColumn(name = "seance_id")
    private Seance seance;

}

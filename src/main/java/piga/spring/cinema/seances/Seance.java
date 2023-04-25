package piga.spring.cinema.seances;

import com.fasterxml.jackson.annotation.JsonFormat;
import piga.spring.cinema.films.Film;
import piga.spring.cinema.salles.Salle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "sceances")
@Getter
@Setter
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private int nombrePlace;

    private float prix;

    /**
     * @ManyToOne Specifie que la relation est de type ManyToOne
     * Un film peut avoir plusieurs sceances
     * Une sceance ne peut avoir qu'un seul
     *
     * @JoinColumn(name = "film_id") Specifie le nom de la colonne qui va contenir la clé étrangère
     */
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;


    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    private Salle salle;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Seance{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", nombrePlace=").append(nombrePlace);
        sb.append(", prix=").append(prix);
        sb.append(", film=").append(film);
        sb.append(", salle=").append(salle);
        sb.append('}');
        return sb.toString();
    }
}

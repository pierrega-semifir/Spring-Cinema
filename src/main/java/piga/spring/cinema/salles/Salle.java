package piga.spring.cinema.salles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "salles")
@Getter
@Setter
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private int numero;

    private int capacite;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Salle{");
        sb.append("id=").append(id);
        sb.append(", numero=").append(numero);
        sb.append(", capacite=").append(capacite);
        sb.append('}');
        return sb.toString();
    }
}

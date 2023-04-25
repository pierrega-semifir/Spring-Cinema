package piga.spring.cinema.seances;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "seances", path = "seances")
@Repository
public interface SeanceRepository extends JpaRepository<Seance, Integer> {
    List<Seance> findByDate(LocalDate date);
}

package piga.spring.cinema.realisateurs.dto;

import piga.spring.cinema.films.dto.FilmReduitDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RealisateurCompletDto {
    private Integer id;
    private String nom;
    private String prenom;
    private List<FilmReduitDto> films = new ArrayList<>();
}

package piga.spring.cinema.films.dto;

import piga.spring.cinema.acteurs.dto.ActeurSansFilmDto;
import piga.spring.cinema.realisateurs.Realisateur;
import piga.spring.cinema.realisateurs.dto.RealisateurSansFilmDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilmCompletDto {
    private Integer id;
    private String titre;
    private String resume;
    private int duree;
    private LocalDate dateSortie;
    private List<ActeurSansFilmDto> acteurs = new ArrayList<>();
    private List<RealisateurSansFilmDto> realisateurs = new ArrayList<>();
}

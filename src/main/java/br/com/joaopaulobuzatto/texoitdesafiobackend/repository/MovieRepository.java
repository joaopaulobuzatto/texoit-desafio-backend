package br.com.joaopaulobuzatto.texoitdesafiobackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.joaopaulobuzatto.texoitdesafiobackend.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMoviesByWinnerOrderByReleaseYearAsc(String string);

}

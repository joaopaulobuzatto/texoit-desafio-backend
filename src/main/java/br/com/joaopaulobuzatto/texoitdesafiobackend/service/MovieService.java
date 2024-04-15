package br.com.joaopaulobuzatto.texoitdesafiobackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.joaopaulobuzatto.texoitdesafiobackend.model.Movie;
import br.com.joaopaulobuzatto.texoitdesafiobackend.model.ProducerResponse;
import br.com.joaopaulobuzatto.texoitdesafiobackend.model.TestResult;
import br.com.joaopaulobuzatto.texoitdesafiobackend.repository.MovieRepository;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updatedMovie) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        updatedMovie.setId(id); // Garante que o ID passado é o mesmo do filme a ser atualizado
        return movieRepository.save(updatedMovie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public TestResult getProducersByBiggestRangeAndSmallestRangeFromYearWinnerConsecutive() {
        List<Movie> movies = findMoviesByWinnerOrderByReleaseYearAsc();
        HashMap<String, List<Integer>> producers = getHashMapProducersAndYears(movies);

        List<ProducerResponse> min = new ArrayList<>();
        List<ProducerResponse> max = new ArrayList<>();

        Integer minWinner = Integer.MAX_VALUE;
        Integer maxWinner = Integer.MIN_VALUE;

        for (Map.Entry<String, List<Integer>> entry : producers.entrySet()) {
            if (entry.getValue().size() == 1) {
                continue;
            }

            String producer = entry.getKey();
            List<Integer> years = entry.getValue();

            Integer minYear = Integer.MAX_VALUE;
            Integer maxYear = Integer.MIN_VALUE;

            Integer previousYear = 0;
            Integer difference;

            for (Integer year : years) {
                if (year < minYear) {
                    minYear = year;
                }
                if (year > maxYear) {
                    maxYear = year;
                }

                difference = year - previousYear;
                if (previousYear != 0 && difference == minWinner) {
                    min.add(new ProducerResponse(producer, difference, previousYear, year));
                }

                if (previousYear != 0 && difference < minWinner) {
                    minWinner = difference;

                    min = new ArrayList<>();
                    min.add(new ProducerResponse(producer, difference, previousYear, year));
                }

                if (previousYear != 0) {
                    difference = maxYear - previousYear;
                    if (difference == maxWinner) {
                        max.add(new ProducerResponse(producer, difference, previousYear, maxYear));
                    }
                    if (difference > maxWinner) {
                        maxWinner = difference;

                        max = new ArrayList<>();
                        max.add(new ProducerResponse(producer, difference, previousYear, maxYear));
                    }
                }
                previousYear = year;
            }
        }

        return new TestResult(min, max);
    }

    private HashMap<String, List<Integer>> getHashMapProducersAndYears(List<Movie> movies) {
        HashMap<String, List<Integer>> producers = new HashMap<>();

        for (Movie movie : movies) {
            String producersByMovie = movie.getProducers().replace(", and", ",").replace(" and", ",");

            for (String producer : producersByMovie.split(", ")) {
                if (producers.containsKey(producer)) {
                    List<Integer> years = producers.get(producer);
                    years.add(movie.getReleaseYear());
                } else {
                    List<Integer> years = new ArrayList<>();
                    years.add(movie.getReleaseYear());
                    producers.put(producer, years);
                }
            }
        }
        return producers;
    }

    private List<Movie> findMoviesByWinnerOrderByReleaseYearAsc() {
        return movieRepository.findMoviesByWinnerOrderByReleaseYearAsc("yes");
    }

}

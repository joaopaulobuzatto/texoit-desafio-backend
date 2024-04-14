package br.com.joaopaulobuzatto.texoitdesafiobackend.config;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.joaopaulobuzatto.texoitdesafiobackend.model.Movie;
import br.com.joaopaulobuzatto.texoitdesafiobackend.repository.MovieRepository;

public class MovieItemWriter implements ItemWriter<Movie> {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public void write(Chunk<? extends Movie> movies) throws Exception {
        // Escrever os filmes no banco de dados
        movieRepository.saveAll(movies);
    }

}

package br.com.joaopaulobuzatto.texoitdesafiobackend.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.joaopaulobuzatto.texoitdesafiobackend.model.Movie;

@Configuration
public class BatchConfig {

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        return new JobBuilder("jobLoadMovies", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(ItemReader<Movie> reader, ItemWriter<Movie> writer, JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepLoadMovies", jobRepository)
                .<Movie, Movie>chunk(50, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Movie> reader() {
        return new FlatFileItemReaderBuilder<Movie>()
                .name("reader")
                .resource(new FileSystemResource("files/movielist.csv"))
                .comments("--")
                .delimited()
                .delimiter(";")
                .names("releaseYear", "title", "studios", "producers", "winner")
                .targetType(Movie.class)
                .build();
    }

    @Bean
    public ItemWriter<Movie> writer() {
        return new MovieItemWriter();
    }

}

package br.com.joaopaulobuzatto.texoitdesafiobackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.joaopaulobuzatto.texoitdesafiobackend.model.TestResult;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MovieIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetProducersByBiggestRangeAndSmallestRangeFromYearWinnerConsecutive() {
        TestResult response = restTemplate.getForObject(
                "/api/movies/producers-by-biggest-range-and-smallest-range-from-year-winner-consecutive",
                TestResult.class);

        assertNotNull(response);
        assertNotEquals(response.min().get(0).producer(), response.max().get(0).producer());
        assertNotEquals(response.min().get(0).interval(), response.max().get(0).interval());
        
    }

    @Test
    public void testGetAllMovies() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/movies", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetMovieById() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/movies/{id}", String.class, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateMovie() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("releaseYear", 2024);
        requestBody.put("title", "Test Movie");
        requestBody.put("studios", "Test Studios");
        requestBody.put("producers", "Test Producer");
        requestBody.put("winner", "yes");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/movies", requestBody, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}

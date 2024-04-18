package br.com.joaopaulobuzatto.texoitdesafiobackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

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
        assertNotNull(response.min());
        assertNotNull(response.max());
        assertNotEquals(0, response.min().size());
        assertNotEquals(0, response.max().size());

        assertEquals(1, response.min().size());
        assertEquals(1, response.max().size());

        Assertions.assertThat(response.min().get(0).interval()).isPositive();
        Assertions.assertThat(response.max().get(0).interval()).isPositive();
        Assertions.assertThat(response.min().get(0).interval()).isLessThan(response.max().get(0).interval());

        /* Testes com dados fixos com base nos dados fornecido na avaliação */
        assertEquals("Matthew Vaughn", response.max().get(0).producer());
        assertEquals(13, response.max().get(0).interval());
        assertEquals(2002, response.max().get(0).previousWin());
        assertEquals(2015, response.max().get(0).followingWin());

        assertEquals("Joel Silver", response.min().get(0).producer());
        assertEquals(1, response.min().get(0).interval());
        assertEquals(1990, response.min().get(0).previousWin());
        assertEquals(1991, response.min().get(0).followingWin());
    }

}

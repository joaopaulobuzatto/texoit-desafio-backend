package br.com.joaopaulobuzatto.texoitdesafiobackend.model;

import java.util.List;

public record TestResult(
        List<ProducerResponse> min,
        List<ProducerResponse> max) {

}

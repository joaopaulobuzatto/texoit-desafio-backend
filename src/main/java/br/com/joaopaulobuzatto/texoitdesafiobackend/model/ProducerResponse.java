package br.com.joaopaulobuzatto.texoitdesafiobackend.model;

public record ProducerResponse(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin) {

}

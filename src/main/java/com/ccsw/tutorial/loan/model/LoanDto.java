package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.time.LocalDate;

public class LoanDto {
    private Long id;

    private LocalDate dateIni;

    private LocalDate dateFinal;

    private GameDto gameDto;


    private ClientDto clientDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateIni() {
        return dateIni;
    }

    public void setDateIni(LocalDate dateIni) {
        this.dateIni = dateIni;
    }

    public LocalDate getDateFinal() {
        return dateFinal;
    }

    public void setDateFinal(LocalDate dateFinal) {
        this.dateFinal = dateFinal;
    }

    public GameDto getGame() {
        return gameDto;
    }

    public void setGame(GameDto gameDto) {
        this.gameDto = gameDto;
    }

    public ClientDto getClient() {
        return clientDto;
    }

    public void setClient(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

}

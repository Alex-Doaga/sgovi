package es.uji.ei1027.sgovi.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Negotiation {

    private Integer idNegotiation;
    private Integer idRequest;
    private Integer idPa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private String negotiationState;

    public Negotiation() {
    }


    public Integer getIdNegotiation() {
        return idNegotiation;
    }

    public void setIdNegotiation(Integer idNegotiation) {
        this.idNegotiation = idNegotiation;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getIdPa() {
        return idPa;
    }

    public void setIdPa(Integer idPa) {
        this.idPa = idPa;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getNegotiationState() {
        return negotiationState;
    }

    public void setNegotiationState(String negotiationState) {
        this.negotiationState = negotiationState;
    }

    @Override
    public String toString() {
        return "Negotiation{" +
                "idNegotiation=" + idNegotiation +
                ", idRequest=" + idRequest +
                ", idPa=" + idPa +
                ", startDate=" + startDate +
                ", negotiationState='" + negotiationState + '\'' +
                '}';
    }
}
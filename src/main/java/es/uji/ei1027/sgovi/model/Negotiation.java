package es.uji.ei1027.sgovi.model;

import java.time.LocalDate;

public class Negotiation {

    private int idNegotiation;
    private int requestId;
    private int paId;
    private LocalDate startDate;

    public Negotiation() {
    }

    public int getIdNegotiation() {
        return idNegotiation;
    }

    public void setIdNegotiation(int idNegotiation) {
        this.idNegotiation = idNegotiation;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getPaId() {
        return paId;
    }

    public void setPaId(int paId) {
        this.paId = paId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Negotiation{" +
                "idNegotiation=" + idNegotiation +
                ", requestId=" + requestId +
                ", paId=" + paId +
                ", startDate=" + startDate +
                '}';
    }
}
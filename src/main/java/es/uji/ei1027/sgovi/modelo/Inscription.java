package es.uji.ei1027.sgovi.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Inscription {
    private Integer idInscription;
    private Integer idActivity;
    private Integer idOviUser;
    private Integer idPa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Boolean hasAttended;

    public Inscription() {
    }


    public Integer getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(Integer idInscription) {
        this.idInscription = idInscription;
    }

    public Integer getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Integer idActivity) {
        this.idActivity = idActivity;
    }

    public Integer getIdOviUser() {
        return idOviUser;
    }

    public void setIdOviUser(Integer idOviUser) {
        this.idOviUser = idOviUser;
    }

    public Integer getIdPa() {
        return idPa;
    }

    public void setIdPa(Integer idPa) {
        this.idPa = idPa;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getHasAttended() {
        return hasAttended;
    }

    public void setHasAttended(Boolean hasAttended) {
        this.hasAttended = hasAttended;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "idInscription=" + idInscription +
                ", idActivity=" + idActivity +
                ", idOviUser=" + idOviUser +
                ", idPa=" + idPa +
                ", date=" + date +
                ", hasAttended=" + hasAttended +
                '}';
    }
}

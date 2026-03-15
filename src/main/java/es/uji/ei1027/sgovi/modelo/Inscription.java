package es.uji.ei1027.sgovi.modelo;

import java.time.LocalDate;

public class Inscription {
    private Integer idInscription;
    private Integer activityId;
    private Integer oviUserId;
    private Integer paId;
    private LocalDate date;

    public Inscription() {
    }

    public Integer getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(Integer idInscription) {
        this.idInscription = idInscription;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getOviUserId() {
        return oviUserId;
    }

    public void setOviUserId(Integer oviUserId) {
        this.oviUserId = oviUserId;
    }

    public Integer getPaId() {
        return paId;
    }

    public void setPaId(Integer paId) {
        this.paId = paId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "idInscription=" + idInscription +
                ", activityId=" + activityId +
                ", oviUserId=" + oviUserId +
                ", paId=" + paId +
                ", date=" + date +
                '}';
    }
}

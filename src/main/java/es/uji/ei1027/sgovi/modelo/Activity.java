package es.uji.ei1027.sgovi.modelo;

import java.time.LocalDate;

public class Activity {
    private Integer idActivity;
    private Integer instructorId;
    private String name;
    private String descripcion;
    private LocalDate date;
    private String place;
    private Integer numberOfParticipants;

    public Activity() {
    }

    public Integer getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(Integer idActivity) {
        this.idActivity = idActivity;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "idActivity=" + idActivity +
                ", instructorId=" + instructorId +
                ", name='" + name + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", date=" + date +
                ", place='" + place + '\'' +
                ", numberOfParticipants=" + numberOfParticipants +
                '}';
    }
}

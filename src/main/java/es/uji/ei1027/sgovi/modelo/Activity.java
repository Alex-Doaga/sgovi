package es.uji.ei1027.sgovi.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Activity {
    private Integer idActivity;
    private String typeActivity;
    private Integer idInstructor;
    private String name;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public String getTypeActivity() {return typeActivity;}

    public void setTypeActivity(String typeActivity) {this.typeActivity = typeActivity;}

    public Integer getIdInstructor() { return idInstructor; }

    public void setIdInstructor(Integer idInstructor) { this.idInstructor = idInstructor; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

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
                ", typeActivity='" + typeActivity + '\'' +
                ", idInstructor=" + idInstructor +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", place='" + place + '\'' +
                ", numberOfParticipants=" + numberOfParticipants +
                '}';
    }
}

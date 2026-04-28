package es.uji.ei1027.sgovi.modelo;

import es.uji.ei1027.sgovi.modelo.enums.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Request {
    private Integer idRequest;
    private Integer oviUserId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    private Integer duration;
    private TypePaEnum typePa;
    private Integer agePa;
    private CityEnum city;
    private HobbiesEnum hobbies;
    private GenderEnum requiredGender;
    private Integer experience;
    private EducationEnum education;
    private StateEnum state;
    private String comments;


    //Constructor
    public Request() {
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getOviUserId() {
        return oviUserId;
    }

    public void setOviUserId(Integer oviUserId) {
        this.oviUserId = oviUserId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public TypePaEnum getTypePa() {
        return typePa;
    }

    public void setTypePa(TypePaEnum typePa) {
        this.typePa = typePa;
    }

    public Integer getAgePa() {
        return agePa;
    }

    public void setAgePa(Integer agePa) {
        this.agePa = agePa;
    }

    public CityEnum getCity() {
        return city;
    }

    public void setCity(CityEnum city) {
        this.city = city;
    }

    public HobbiesEnum getHobbies() {
        return hobbies;
    }

    public void setHobbies(HobbiesEnum hobbies) {
        this.hobbies = hobbies;
    }

    public GenderEnum getRequiredGender() {
        return requiredGender;
    }

    public void setRequiredGender(GenderEnum requiredGender) {
        this.requiredGender = requiredGender;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public EducationEnum getEducation() {
        return education;
    }

    public void setEducation(EducationEnum education) {
        this.education = education;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Request{" +
                "idRequest=" + idRequest +
                ", oviUserId=" + oviUserId +
                ", requestDate=" + requestDate +
                ", startDate=" + startDate +
                ", duration=" + duration +
                ", typePa=" + typePa +
                ", agePa=" + agePa +
                ", city=" + city +
                ", hobbies=" + hobbies +
                ", requiredGender=" + requiredGender +
                ", experience=" + experience +
                ", education=" + education +
                ", state=" + state +
                ", comments='" + comments + '\'' +
                '}';
    }
}

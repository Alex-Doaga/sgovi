package es.uji.ei1027.sgovi.modelo;

import es.uji.ei1027.sgovi.modelo.enums.State;
import es.uji.ei1027.sgovi.modelo.enums.TypeAccompaniment;
import es.uji.ei1027.sgovi.modelo.enums.TypePa;

import java.time.LocalDate;

public class Request {
    private Integer idRequest;
    private Integer oviUserId;
    private LocalDate requestDate;
    private LocalDate startDate;
    private Integer duration;
    private TypePa typePA; // Enum
    private TypeAccompaniment typeService; // Enum
    private Integer agePA;
    private String hobbies;
    private String requiredGender;
    private String schedule;
    private State state; // Enum
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

    public TypePa getTypePA() {
        return typePA;
    }

    public void setTypePA(TypePa typePA) {
        this.typePA = typePA;
    }

    public TypeAccompaniment getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeAccompaniment typeService) {
        this.typeService = typeService;
    }

    public Integer getAgePA() {
        return agePA;
    }

    public void setAgePA(Integer agePA) {
        this.agePA = agePA;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getRequiredGender() {
        return requiredGender;
    }

    public void setRequiredGender(String requiredGender) {
        this.requiredGender = requiredGender;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
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
                ", typePA=" + typePA +
                ", typeService=" + typeService +
                ", agePA=" + agePA +
                ", hobbies='" + hobbies + '\'' +
                ", requiredGender='" + requiredGender + '\'' +
                ", schedule='" + schedule + '\'' +
                ", state=" + state +
                ", comments='" + comments + '\'' +
                '}';
    }
}

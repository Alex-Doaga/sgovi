package es.uji.ei1027.sgovi.modelo;



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
    private String typePa;
    private Integer agePa;
    private String city;
    private String hobbies;
    private String requiredGender;
    private String experience;
    private String education;
    private String state;
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

    public String getTypePa() {
        return typePa;
    }

    public void setTypePa(String typePa) {
        this.typePa = typePa;
    }

    public Integer getAgePa() {
        return agePa;
    }

    public void setAgePa(Integer agePA) {
        this.agePa = agePA;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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
                ", typePa='" + typePa + '\'' +
                ", agePA=" + agePa +
                ", city='" + city + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", requiredGender='" + requiredGender + '\'' +
                ", experience='" + experience + '\'' +
                ", education='" + education + '\'' +
                ", state='" + state + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}

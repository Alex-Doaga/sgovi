package es.uji.ei1027.sgovi.modelo;

import es.uji.ei1027.sgovi.modelo.enums.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PA {
    private Integer idPa;
    private String dniNie;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private TypePaEnum typePa;
    //private String typeService;
    private String address;
    private EducationEnum education;
    private Integer experience;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private CityEnum city;
    private GenderEnum gender;
    private Integer postalCode;
    private HobbiesEnum hobbies;
    private String comments;
    private String cv;
    private StateEnum paState;
    private String rejectionReason;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate availabilityEndDate;

    public PA() {
    }

    public Integer getIdPa() {
        return idPa;
    }

    public void setIdPa(Integer idPa) {
        this.idPa = idPa;
    }

    public String getDniNie() {
        return dniNie;
    }

    public void setDniNie(String dniNie) {
        this.dniNie = dniNie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TypePaEnum getTypePa() {
        return typePa;
    }

    public void setTypePa(TypePaEnum typePa) {
        this.typePa = typePa;
    }

    /*public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }*/

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EducationEnum getEducation() {
        return education;
    }

    public void setEducation(EducationEnum education) {
        this.education = education;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public CityEnum getCity() {
        return city;
    }

    public void setCity(CityEnum city) {
        this.city = city;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public HobbiesEnum getHobbies() {
        return hobbies;
    }

    public void setHobbies(HobbiesEnum hobbies) {
        this.hobbies = hobbies;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public StateEnum getPaState() {
        return paState;
    }

    public void setPaState(StateEnum paState) {
        this.paState = paState;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getAvailabilityStartDate() {
        return availabilityStartDate;
    }

    public void setAvailabilityStartDate(LocalDate availabilityStartDate) {
        this.availabilityStartDate = availabilityStartDate;
    }

    public LocalDate getAvailabilityEndDate() {
        return availabilityEndDate;
    }

    public void setAvailabilityEndDate(LocalDate availabilityEndDate) {
        this.availabilityEndDate = availabilityEndDate;
    }

    @Override
    public String toString() {
        return "PA{" +
                "idPa=" + idPa +
                ", dniNie='" + dniNie + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", typePa=" + typePa +
                //", typeService='" + typeService + '\'' +
                ", address='" + address + '\'' +
                ", education=" + education +
                ", experience=" + experience +
                ", birthDate=" + birthDate +
                ", city=" + city +
                ", gender=" + gender +
                ", postalCode=" + postalCode +
                ", hobbies=" + hobbies +
                ", comments='" + comments + '\'' +
                ", cv='" + cv + '\'' +
                ", paState=" + paState +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", password='" + password + '\'' +
                ", availabilityStartDate=" + availabilityStartDate +
                ", availabilityEndDate=" + availabilityEndDate +
                '}';
    }
}

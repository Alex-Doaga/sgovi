package es.uji.ei1027.sgovi.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PA {
    private Integer idPa;
    private String dniNie;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String typePa;
    private String typeService;
    private String address;
    private String education;
    private String entity;
    private String experience;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String city;
    private String gender;
    private String postalCode;
    private String hobbies;
    private String comments;
    private String cv;
    private String paState;
    private String rejectionReason;
    private String password;

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

    public String getTypePa() {
        return typePa;
    }

    public void setTypePa(String typePa) {
        this.typePa = typePa;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() { return postalCode; }

    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) { this.hobbies = hobbies; }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPaState() {
        return paState;
    }

    public void setPaState(String paState) {
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

    @Override
    public String toString() {
        return "PA{" +
                "idPa='" + idPa + '\'' +
                ", dniNie='" + dniNie + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", typePa=" + typePa +
                ", typeService='" + typeService + '\'' +
                ", address='" + address + '\'' +
                ", education='" + education + '\'' +
                ", entity='" + entity + '\'' +
                ", experience='" + experience + '\'' +
                ", birthDate=" + birthDate +
                ", city='" + city + '\'' +
                ", gender='" + gender + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", comments='" + comments + '\'' +
                ", cv='" + cv + '\'' +
                ", paState='" + paState + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }
}

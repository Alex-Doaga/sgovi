package es.uji.ei1027.sgovi.modelo;

import es.uji.ei1027.sgovi.modelo.enums.TypePa;

import java.sql.Date;

public class PA {
    private String idPa;
    private String dniNie;
    private String name;
    private String surname;
    private String email;
    private TypePa typePa;
    private String address;
    private String education;
    private String entity;
    private Boolean hasExperience;
    private String experience;
    private Date birthDate;
    private String city;
    private String postalCode;
    private String hobbies;
    private String comments;
    private String cv;

    public PA() {
    }

    public String getIdPa() { return idPa; }

    public void setIdPa(String idPa) {
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

    public TypePa getTypePa() {
        return typePa;
    }

    public void setTypePa(TypePa typePa) {
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

    public Boolean getHasExperience() {
        return hasExperience;
    }

    public void setHasExperience(Boolean hasExperience) {
        this.hasExperience = hasExperience;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    @Override
    public String toString() {
        return "PA{" +
                "idPa='" + idPa + '\'' +
                ", dniNie='" + dniNie + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", typePa=" + typePa +
                ", address='" + address + '\'' +
                ", education='" + education + '\'' +
                ", entity='" + entity + '\'' +
                ", hasExperience=" + hasExperience +
                ", experience='" + experience + '\'' +
                ", birthDate=" + birthDate +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", comments='" + comments + '\'' +
                ", cv='" + cv + '\'' +
                '}';
    }
}

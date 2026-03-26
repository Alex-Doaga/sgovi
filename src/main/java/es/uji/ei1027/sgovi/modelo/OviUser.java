package es.uji.ei1027.sgovi.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class OviUser {
    private Integer idOviUser;
    private String name;
    private String surname;
    private String dniNie;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String address;
    private String city;
    private String postalCode;
    private String email;
    private String phone;
    private String entity;
    private String nameTutor;
    private String dniNieTutor;
    private Boolean hasDepenDegree;
    private Integer depenDegree;
    private String projectLifeDoc;
    private String socialServiceCenter;


    //Constructor
    public OviUser() {
    }

    public Integer getIdOviUser() {
        return idOviUser;
    }

    public void setIdOviUser(Integer idOviUser) {
        this.idOviUser = idOviUser;
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

    public String getDniNie() {
        return dniNie;
    }

    public void setDniNie(String dniNie) {
        this.dniNie = dniNie;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getNameTutor() {
        return nameTutor;
    }

    public void setNameTutor(String nameTutor) {
        this.nameTutor = nameTutor;
    }

    public String getDniNieTutor() {
        return dniNieTutor;
    }

    public void setDniNieTutor(String dniNieTutor) {
        this.dniNieTutor = dniNieTutor;
    }

    public Boolean getHasDepenDegree() {
        return hasDepenDegree;
    }

    public void setHasDepenDegree(Boolean hasDepenDegree) {
        this.hasDepenDegree = hasDepenDegree;
    }

    public Integer getDepenDegree() {
        return depenDegree;
    }

    public void setDepenDegree(Integer depenDegree) {
        this.depenDegree = depenDegree;
    }

    public String getProjectLifeDoc() {
        return projectLifeDoc;
    }

    public void setProjectLifeDoc(String projectLifeDoc) {
        this.projectLifeDoc = projectLifeDoc;
    }

    public String getSocialServiceCenter() {
        return socialServiceCenter;
    }

    public void setSocialServiceCenter(String socialServiceCenter) {
        this.socialServiceCenter = socialServiceCenter;
    }

    @Override
    public String toString() {
        return "OviUser{" +
                "idOviUser=" + idOviUser +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dniNie='" + dniNie + '\'' +
                ", birthDate=" + birthDate +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", entity='" + entity + '\'' +
                ", nameTutor='" + nameTutor + '\'' +
                ", dniNieTutor='" + dniNieTutor + '\'' +
                ", hasDepenDegree=" + hasDepenDegree +
                ", depenDegree=" + depenDegree +
                ", projectLifeDoc='" + projectLifeDoc + '\'' +
                ", socialServiceCenter='" + socialServiceCenter + '\'' +
                '}';
    }
}
package es.uji.ei1027.sgovi.modelo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Contract {
    private Integer idContract;
    private Integer idRequest;
    private Integer idPa;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private String contractDocument;
    private String contractState;


    //Constructor
    public Contract() {
    }


    public Integer getIdContract() {
        return idContract;
    }

    public void setIdContract(Integer idContract) {
        this.idContract = idContract;
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public Integer getIdPa() {
        return idPa;
    }

    public void setIdPa(Integer idPa) {
        this.idPa = idPa;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContractDocument() {
        return contractDocument;
    }

    public void setContractDocument(String contractDocument) {
        this.contractDocument = contractDocument;
    }

    public String getContractState() {
        return contractState;
    }

    public void setContractState(String contractState) {
        this.contractState = contractState;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "idContract=" + idContract +
                ", idRequest=" + idRequest +
                ", idPa=" + idPa +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", contractDocument='" + contractDocument + '\'' +
                ", contractState='" + contractState + '\'' +
                '}';
    }
}
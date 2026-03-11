package es.uji.ei1027.sgovi.modelo;

import java.time.LocalDate;

public class Contract {
    private Integer idContract;
    private Integer requestId;
    private Integer paId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractDocument;


    //Constructor
    public Contract() {
    }

    public Integer getIdContract() {
        return idContract;
    }

    public void setIdContract(Integer idContract) {
        this.idContract = idContract;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getPaId() {
        return paId;
    }

    public void setPaId(Integer paId) {
        this.paId = paId;
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

    @Override
    public String toString() {
        return "Contract{" +
                "idContract=" + idContract +
                ", requestId=" + requestId +
                ", paId=" + paId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", contractDocument='" + contractDocument + '\'' +
                '}';
    }
}
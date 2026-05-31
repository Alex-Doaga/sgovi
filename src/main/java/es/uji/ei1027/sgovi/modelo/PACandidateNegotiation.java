package es.uji.ei1027.sgovi.modelo;

public class PACandidateNegotiation {
    private PA pa;
    private String negotiationState;
    private String contractState;
    private int requestId;

    public PA getPa() {
        return pa;
    }

    public void setPa(PA pa) {
        this.pa = pa;
    }

    public String getNegotiationState() {
        return negotiationState;
    }

    public void setNegotiationState(String negotiationState) {
        this.negotiationState = negotiationState;
    }

    public String getContractState() {
        return contractState;
    }

    public void setContractState(String contract) {
        this.contractState = contract;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}

package es.uji.ei1027.sgovi.modelo;

public class PANegotiation {
    private OviUser oviUser;
    private Negotiation negotiation;
    private Request request;

    public OviUser getOviUser() {
        return oviUser;
    }

    public void setOviUser(OviUser oviUser) {
        this.oviUser = oviUser;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Negotiation getNegotiation() {
        return negotiation;
    }

    public void setNegotiation(Negotiation negotiation) {
        this.negotiation = negotiation;
    }
}

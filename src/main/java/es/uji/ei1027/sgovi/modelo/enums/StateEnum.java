package es.uji.ei1027.sgovi.modelo.enums;

//Enumeración para el estado de una solicitud
public enum StateEnum {
    ACCEPTED("Acceptat"),
    REFUSED("Rebutjat"),
    PENDING("Pendent");

    private final String label;

    StateEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

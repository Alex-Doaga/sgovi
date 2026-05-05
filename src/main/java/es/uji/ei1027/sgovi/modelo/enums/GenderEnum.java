package es.uji.ei1027.sgovi.modelo.enums;

//Enumeración para el género de un PA
public enum GenderEnum {
    FEMALE("Dona"),
    MALE("Home");

    private final String label;

    GenderEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

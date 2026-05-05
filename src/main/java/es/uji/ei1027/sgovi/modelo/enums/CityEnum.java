package es.uji.ei1027.sgovi.modelo.enums;

public enum CityEnum {
    CASTELLON("Castelló"),
    VILLARREAL("Villarreal"),
    BURRIANA("Borriana");

    private String label;

    CityEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

package es.uji.ei1027.sgovi.modelo.enums;

public enum HobbiesEnum {
    READING("Llegir"),
    TRAVELLING("Viatjar"),
    COOKING("Cuinar"),
    PLAYING_SPORTS("Practicar esports");

    private final String label;

    HobbiesEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

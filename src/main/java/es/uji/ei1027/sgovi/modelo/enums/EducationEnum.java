package es.uji.ei1027.sgovi.modelo.enums;

//Enumeración para saber el grado de educación de un PA
public enum EducationEnum {
    HIGH_SCHOOL("Bachillerat"),
    VOCATIONAL_TRAINING("FP"),
    UNIVERSITY_DEGREE("Grau universitari");

    private final String label;

    EducationEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

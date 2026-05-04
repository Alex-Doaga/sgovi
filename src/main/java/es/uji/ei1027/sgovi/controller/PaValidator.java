package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

public class PaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PA.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PA pa = (PA) target;

        // 1. Cadenas de texto obligatorias (NOT NULL en base de datos)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "obligatori", "El nom és obligatori.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "obligatori", "Els cognoms són obligatoris.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dniNie", "obligatori", "El DNI/NIE és obligatori.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "obligatori", "L'adreça és obligatòria.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "obligatori", "El codi postal és obligatori.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "obligatori", "El correu electrònic és obligatori.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "obligatori", "El telèfon és obligatori.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "obligatori", "La contrasenya és obligatòria.");

        // 2. Objetos/Desplegables obligatorios (NOT NULL en base de datos)
        if (pa.getCity() == null) {
            errors.rejectValue("city", "obligatori", "Has de seleccionar una ciutat.");
        }
        if (pa.getTypePa() == null) {
            errors.rejectValue("typePa", "obligatori", "El tipus d'assistent és obligatori.");
        }
        if (pa.getEducation() == null) {
            errors.rejectValue("education", "obligatori", "El nivell d'educació és obligatori.");
        }
        if (pa.getExperience() == null) {
            errors.rejectValue("experience", "obligatori", "L'experiència és obligatòria.");
        }

        // 3. Validación de la Fecha de Nacimiento (NOT NULL y mayor de edad)
        if (pa.getBirthDate() == null) {
            errors.rejectValue("birthDate", "obligatori", "La data de naixement és obligatòria.");
        } else {
            Period period = Period.between(pa.getBirthDate(), LocalDate.now());
            if (period.getYears() < 18) {
                errors.rejectValue("birthDate", "edat", "Has de ser major d'edat (18 anys) per registrar-te.");
            }
        }

        // 4. Validaciones de formato extra
        if (pa.getDniNie() != null && !pa.getDniNie().trim().isEmpty()) {
            if (pa.getDniNie().length() != 9) {
                errors.rejectValue("dniNie", "format", "El DNI/NIE ha de tindre 9 caràcters.");
            }
        }

        if (pa.getEmail() != null && !pa.getEmail().trim().isEmpty()) {
            if (!pa.getEmail().contains("@") || !pa.getEmail().contains(".")) {
                errors.rejectValue("email", "format", "Format de correu electrònic invàlid.");
            }
        }

        if (pa.getPassword() != null && !pa.getPassword().trim().isEmpty()) {
            if (pa.getPassword().length() < 8) {
                errors.rejectValue("password", "curta", "La contrasenya ha de tindre almenys 8 caràcters.");
            }
        }
    }
}
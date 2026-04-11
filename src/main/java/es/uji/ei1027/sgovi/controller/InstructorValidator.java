package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.modelo.Instructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class InstructorValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Instructor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Instructor instructor = (Instructor) target;

        // Validar Nom
        if (instructor.getName() == null || instructor.getName().trim().isEmpty()) {
            errors.rejectValue("name", "obligatori", "El nom és obligatori");
        }

        // Validar Cognoms
        if (instructor.getSurname() == null || instructor.getSurname().trim().isEmpty()) {
            errors.rejectValue("surname", "obligatori", "Els cognoms són obligatoris");
        }

        // Validar DNI/NIE
        if (instructor.getDniNie() == null || instructor.getDniNie().trim().isEmpty()) {
            errors.rejectValue("dniNie", "obligatori", "El DNI/NIE és obligatori");
        } else if (instructor.getDniNie().trim().length() != 9) {
            errors.rejectValue("dniNie", "format", "El DNI/NIE ha de tindre exactament 9 caràcters");
        }

        // Validar Data de Naixement
        if (instructor.getBirthDate() == null) {
            errors.rejectValue("birthDate", "obligatori", "La data de naixement és obligatòria");
        } else if (instructor.getBirthDate().isAfter(LocalDate.now())) {
            errors.rejectValue("birthDate", "incoherent", "La data de naixement no pot ser en el futur");
        }

        // Validar Email
        if (instructor.getEmail() == null || instructor.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "obligatori", "L'email és obligatori");
        } else if (!instructor.getEmail().contains("@")) {
            errors.rejectValue("email", "format", "L'email ha de ser una adreça vàlida");
        }

        // Validar Telèfon
        if (instructor.getPhone() == null || instructor.getPhone().trim().isEmpty()) {
            errors.rejectValue("phone", "obligatori", "El telèfon és obligatori");
        } else if (instructor.getPhone().trim().length() < 9) {
            errors.rejectValue("phone", "format", "El telèfon ha de tindre almenys 9 dígits");
        }

        // Validar Especialització
        if (instructor.getSpecialization() == null || instructor.getSpecialization().trim().isEmpty()) {
            errors.rejectValue("specialization", "obligatori", "L'especialitat és obligatòria");
        }
    }
}
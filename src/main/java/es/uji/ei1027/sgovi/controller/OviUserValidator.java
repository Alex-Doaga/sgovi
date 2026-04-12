package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.modelo.OviUser;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

public class OviUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return OviUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OviUser oviUser = (OviUser) target;

        // Validar Nom
        if (oviUser.getName() == null || oviUser.getName().trim().isEmpty()) {
            errors.rejectValue("name", "obligatori", "El nom és obligatori");
        }

        // Validar Cognoms
        if (oviUser.getSurname() == null || oviUser.getSurname().trim().isEmpty()) {
            errors.rejectValue("surname", "obligatori", "Els cognoms són obligatoris");
        }

        // Validar DNI/NIE
        if (oviUser.getDniNie() == null || oviUser.getDniNie().trim().isEmpty()) {
            errors.rejectValue("dniNie", "obligatori", "El DNI/NIE és obligatori");
        } else if (oviUser.getDniNie().trim().length() != 9) {
            errors.rejectValue("dniNie", "format", "El DNI/NIE ha de tindre exactament 9 caràcters");
        }

        // Validar Data de Naixement (No nula i no futura)
        if (oviUser.getBirthDate() == null) {
            errors.rejectValue("birthDate", "obligatori", "La data de naixement és obligatòria");
        } else if (oviUser.getBirthDate().isAfter(LocalDate.now())) {
            errors.rejectValue("birthDate", "incoherent", "La data de naixement no pot ser en el futur");
        }

        // Validar Email
        if (oviUser.getEmail() == null || oviUser.getEmail().trim().isEmpty()) {
            errors.rejectValue("email", "obligatori", "L'email és obligatori");
        } else if (!oviUser.getEmail().contains("@")) {
            errors.rejectValue("email", "format", "L'email ha de ser una adreça vàlida");
        }

        // Validar Telèfon
        if (oviUser.getPhone() == null || oviUser.getPhone().trim().isEmpty()) {
            errors.rejectValue("phone", "obligatori", "El telèfon és obligatori");
        } else if (oviUser.getPhone().trim().length() < 9) {
            errors.rejectValue("phone", "format", "El telèfon ha de tindre almenys 9 dígits");
        }

        // Validar Adreça
        if (oviUser.getAddress() == null || oviUser.getAddress().trim().isEmpty()) {
            errors.rejectValue("address", "obligatori", "L'adreça és obligatòria");
        }

        // Validar Ciutat
        if (oviUser.getCity() == null || oviUser.getCity().trim().isEmpty()) {
            errors.rejectValue("city", "obligatori", "La ciutat és obligatòria");
        }

        // Validar Codi Postal
        if (oviUser.getPostalCode() == null || oviUser.getPostalCode().trim().isEmpty()) {
            errors.rejectValue("postalCode", "obligatori", "El codi postal és obligatori");
        } else if (!oviUser.getPostalCode().matches("\\d{5}")) {
            errors.rejectValue("postalCode", "format", "El codi postal ha de tindre exactament 5 números");
        }

        // Validar Entitat
        if (oviUser.getEntity() == null || oviUser.getEntity().trim().isEmpty()) {
            errors.rejectValue("entity", "obligatori", "L'entitat és obligatòria");
        }

        // Validar DNI/NIE del Tutor si se pone
        if (oviUser.getDniNieTutor() != null && !oviUser.getDniNieTutor().trim().isEmpty()) {
            if (oviUser.getDniNieTutor().trim().length() != 9) {
                errors.rejectValue("dniNieTutor", "format", "El DNI/NIE del tutor ha de tindre exactament 9 caràcters");
            }
        }

        // Validar Grau de Dependència (entre 1 y 3 si marca que tiene)
        if (Boolean.TRUE.equals(oviUser.getHasDepenDegree())) {
            if (oviUser.getDepenDegree() == null || oviUser.getDepenDegree() < 1 || oviUser.getDepenDegree() > 3) {
                errors.rejectValue("depenDegree", "format", "Si tens grau de dependència, has d'indicar un valor entre 1 i 3");
            }
        }

        // Validar URL del Projecte de Vida si se pone
        if (oviUser.getProjectLifeDoc() != null && !oviUser.getProjectLifeDoc().trim().isEmpty()) {
            String url = oviUser.getProjectLifeDoc().trim().toLowerCase();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                errors.rejectValue("projectLifeDoc", "format", "L'enllaç ha de començar per http:// o https://");
            }
        }

        // Validar Contrasenya (Només obligatòria quan CREEM un usuari nou)
        // Sabem que és nou si el seu ID encara és null
        if (oviUser.getIdOviUser() == null) {
            if (oviUser.getPassword() == null || oviUser.getPassword().trim().isEmpty()) {
                errors.rejectValue("password", "obligatori", "La contrasenya és obligatòria per a nous usuaris");
            } else if (oviUser.getPassword().length() < 6) {
                errors.rejectValue("password", "format", "La contrasenya ha de tindre almenys 6 caràcters");
            }
        }
    }
}
package es.uji.ei1027.sgovi.controller; // Ajusta el paquete si es necesario

import es.uji.ei1027.sgovi.modelo.Request;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;

public class RequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Request.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Request request = (Request) target;

        // 1. Validar Data d'Inici (startDate)
        if (request.getStartDate() == null) {
            errors.rejectValue("startDate", "obligatori", "La data d'inici és obligatòria.");
        } else if (request.getStartDate().isBefore(LocalDate.now())) {
            errors.rejectValue("startDate", "invalid", "La data d'inici no pot ser anterior a la data d'avui.");
        }

        // 2. Validar Duració (duration)
        if (request.getDuration() == null) {
            errors.rejectValue("duration", "obligatori", "Has d'indicar la duració en mesos.");
        } else if (request.getDuration() <= 0) {
            errors.rejectValue("duration", "invalid", "La duració ha de ser d'almenys 1 mes.");
        }

        // 3. Validar Tipus de PA (typePa)
        if (request.getTypePa() == null) {
            errors.rejectValue("typePa", "obligatori", "El tipus d'Assistent Personal és obligatori.");
        }

        // 4. Validar Edat del PA (agePa)
        if (request.getAgePa() == null) {
            errors.rejectValue("agePa", "obligatori", "L'edat requerida és obligatòria.");
        } else if (request.getAgePa() < 18) {
            errors.rejectValue("agePa", "invalid", "L'edat mínima requerida ha de ser 18 anys.");
        }

        // 5. Validar Ciutat (city)
        if (request.getCity() == null) {
            errors.rejectValue("city", "obligatori", "La ciutat on es prestarà el servei és obligatòria.");
        }

        // 6. Validar Aficions (hobbies)
        if (request.getHobbies() == null) {
            errors.rejectValue("hobbies", "obligatori", "Has de seleccionar una afició.");
        }

        // 7. Validar Gènere Requerit (requiredGender)
        if (request.getRequiredGender() == null) {
            errors.rejectValue("requiredGender", "obligatori", "Has d'indicar el gènere requerit per a l'assistent.");
        }

        // 8. Validar Experiència (experience)
        if (request.getExperience() == null) {
            errors.rejectValue("experience", "obligatori", "L'experiència mínima requerida és obligatòria.");
        } else if (request.getExperience() < 0) {
            errors.rejectValue("experience", "invalid", "L'experiència no pot ser un nombre negatiu.");
        }

        // 9. Validar Educació (education)
        if (request.getEducation() == null) {
            errors.rejectValue("education", "obligatori", "El nivell d'estudis requerit és obligatori.");
        }

        // Nota: 'comments' y 'state' no los validamos como obligatorios porque
        // state se asigna automáticamente (suele empezar en 'pending')
        // y los comentarios suelen ser opcionales.
    }
}
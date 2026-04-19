package es.uji.ei1027.sgovi.validadores;

import es.uji.ei1027.sgovi.modelo.Activity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ActivityValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Activity.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Activity activity = (Activity) obj;
        if (activity.getName().trim().equals(""))
            errors.rejectValue("name", "obligatori", "Cal introduir un nom");
        if (activity.getTypeActivity() == null)
            errors.rejectValue("typeActivity", "obligatori", "Cal seleccionar un tipus");
        if (activity.getIdInstructor() == null)
            errors.rejectValue("idInstructor", "obligatori", "Cal assignar un ID de formador");
        if (activity.getDate() == null)
            errors.rejectValue("date", "obligatori", "La data és obligatòria");
    }
}
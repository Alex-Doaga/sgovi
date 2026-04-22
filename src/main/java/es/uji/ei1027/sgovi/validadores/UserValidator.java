package es.uji.ei1027.sgovi.validadores;


import es.uji.ei1027.sgovi.modelo.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {


    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }


    @Override
    public void validate(Object obj, Errors errors) {
        UserDetails userDetails = (UserDetails) obj;

        if (userDetails.getUsername() == null || userDetails.getUsername().trim().isEmpty()) {
            errors.rejectValue("username", "obligatori", "L'usuari no pot estar buit");
        }

        // Validar que la contraseña no esté vacía
        if (userDetails.getPassword() == null || userDetails.getPassword().trim().isEmpty()) {
            errors.rejectValue("password", "obligatori", "La contrasenya no pot estar buida");
        }
    }
    }
}

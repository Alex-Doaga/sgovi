package es.uji.ei1027.sgovi.controller;
import es.uji.ei1027.sgovi.dao.UserDao;
import es.uji.ei1027.sgovi.modelo.UserDetails;
import es.uji.ei1027.sgovi.validadores.UserValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;


@Controller
public class LoginController {


    @Autowired
    private UserDao userDao;


    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDetails());
        return "login";
    }


    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult, HttpSession session) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        user = userDao.loadUserByUsername(user.getUsername(),
                user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw",
                    "Contrasenya incorrecta");
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió  ③
        session.setAttribute("user", user);
        // Aci podrem restaurar on tornar si ho existeix en la sessió, ④
        // i en altre cas...
        // Torna a la pàgina principal
        return "redirect:/";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}

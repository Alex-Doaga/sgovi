package es.uji.ei1027.sgovi.controlador;

import es.uji.ei1027.sgovi.dao.OviUserDao;
import es.uji.ei1027.sgovi.modelo.OviUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/oviuser")
public class OviUserController {

    private OviUserDao oviUserDao;

    @Autowired
    public void setOviUserDao(OviUserDao oviUserDao) {
        this.oviUserDao = oviUserDao;
    }

    // Operacions: Crear, llistar, actualitzar, esborrar

    //Operación listar todos los oviUsers
    @RequestMapping("/list")
    public String listOviUsers(Model model) {
        // Obtenemos los usuarios del DAO y los guardamos en el modelo bajo el nombre "oviUsers"
        model.addAttribute("oviUsers", oviUserDao.getOviUsers());
        // Devolvemos el nombre de la plantilla HTML a renderizar
        return "oviuser/list";
    }

    //Crear oviuser
    @RequestMapping(value="/add")
    public String addOviUser(Model model) {
        model.addAttribute("oviuser", new OviUser());
        return "oviuser/add";
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviuser") OviUser oviuser,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "oviuser/add";
        oviUserDao.addOviUser(oviuser);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id_ovi_user}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable int id_ovi_user) {
        model.addAttribute("oviuser", oviUserDao.getOviUser(id_ovi_user));
        return "oviuser/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("oviuser") OviUser oviuser,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "oviuser/update";
        oviUserDao.updateOviUser(oviuser);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{id_ovi_user}")
    public String processDelete(@PathVariable int id_ovi_user) {
        oviUserDao.deleteOviUser(id_ovi_user);
        return "redirect:../list";
    }
}
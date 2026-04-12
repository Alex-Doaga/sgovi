package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.OviUserDao;
import es.uji.ei1027.sgovi.modelo.OviUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ovi-user")
public class OviUserController {

    private OviUserDao oviUserDao;

    @Autowired
    public void setOviUserDao(OviUserDao oviUserDao) {
        this.oviUserDao = oviUserDao;
    }

    // Operacions: llistar, crear, actualitzar, esborrar

    // ==========================================
    //   LISTAR Ovi Users
    // ==========================================

    //Operación listar todos los oviUsers
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("oviUsers", oviUserDao.getOviUsers());
        return "ovi-user/list";
    }

    // Operación listar los oviUsers por estado de registro (aceptados/pendientes/rechazados)
    @RequestMapping("/list/{state}")
    public String listByState(Model model, @PathVariable String state) {
        model.addAttribute("oviUsers", oviUserDao.getOviUsersByState(state));
        return "ovi-user/list-" + state;
    }

    // ==========================================
    //   CREAR/AÑADIR Ovi User
    // ==========================================

    // Formulario de añadir usuario
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addOviUser(Model model) {
        model.addAttribute("oviUser", new OviUser());
        return "ovi-user/add";
    }

    // Procesar el formulario de añadir con VALIDACIÓN
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviUser") OviUser oviUser,
                                   BindingResult bindingResult) {

        // Instanciar y ejecutar el validador
        OviUserValidator oviUserValidator = new OviUserValidator();
        oviUserValidator.validate(oviUser, bindingResult);

        // Si hay errores, volvemos a la vista del formulario
        if (bindingResult.hasErrors()) {
            return "ovi-user/add";
        }

        // Si no hay errores, guardamos
        oviUserDao.addOviUser(oviUser);
        return "redirect:/ovi-user/list/pending";
    }

    // ==========================================
    //   MODIFICAR/ACTUALIZAR Ovi User
    // ==========================================

    // Mostrar formulario de edición
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editOviUser(Model model, @PathVariable int id) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(id));
        return "ovi-user/update";
    }

    // Procesar el formulario de edición con VALIDACIÓN
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("oviUser") OviUser oviUser,
                                      BindingResult bindingResult) {

        // Instanciar y ejecutar el validador
        OviUserValidator oviUserValidator = new OviUserValidator();
        oviUserValidator.validate(oviUser, bindingResult);

        // Si hay errores en la edición, volvemos a la vista de update
        if (bindingResult.hasErrors()) {
            return "ovi-user/update";
        }

        // Si todo es correcto, actualizamos
        oviUserDao.updateOviUser(oviUser);
        return "redirect:/ovi-user/list/accepted";
    }

    // ==========================================
    //   GESTIÓN DE ACEPTAR / RECHAZAR
    // ==========================================

    // Aceptar solicitud directamente
    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST)
    public String acceptOviUser(@PathVariable int id) {
        oviUserDao.updateOviUserState(id, "accepted", "");
        return "redirect:/ovi-user/list/pending";
    }

    // Mostrar formulario para rechazar (Pide el motivo)
    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String rejectOviUserForm(Model model, @PathVariable int id) {
        OviUser oviUser = new OviUser();
        oviUser.setIdOviUser(id);
        model.addAttribute("oviUser", oviUser);
        return "ovi-user/reject";
    }

    // Procesar el rechazo guardando el motivo
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String processRejectOviUser(@ModelAttribute("oviUser") OviUser oviUser) {
        oviUserDao.updateOviUserState(
                oviUser.getIdOviUser(),
                "refused",
                oviUser.getRejectionReason()
        );
        return "redirect:/ovi-user/list/pending";
    }

    // ==========================================
    //   BORRADO DE OVI USER
    // ==========================================

    // Mostrar la página de confirmación de borrado (GET)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteConfirm(Model model, @PathVariable int id) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(id));
        return "ovi-user/delete";
    }

    // Ejecutar el borrado real tras confirmar (POST)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processDelete(
            @ModelAttribute("oviUser") OviUser oviUser) {
        oviUserDao.deleteOviUser(oviUser.getIdOviUser());
        return "redirect:list";
    }
}
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

    // Operacions: Crear, llistar, actualitzar, esborrar

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

    // Crear oviUser
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("oviUser", new OviUser());
        return "ovi-user/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(
            @ModelAttribute("oviUser") OviUser oviUser,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ovi-user/add";
        oviUserDao.addOviUser(oviUser);
        return "redirect:list";
    }

    // Modificar oviuser
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(id));
        return "ovi-user/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdate(
            @ModelAttribute("oviUser") OviUser oviUser,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "ovi-user/update";
        oviUserDao.updateOviUser(oviUser);
        return "redirect:list";
    }

    // ==========================================
    //   NUEVO: GESTIÓN DE ACEPTAR / RECHAZAR
    // ==========================================

    // 1. Aceptar solicitud directamente (POST para mayor seguridad)
    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST)
    public String acceptOviUser(@PathVariable int id) {
        oviUserDao.updateOviUserState(id, "accepted", "");
        return "redirect:/ovi-user/list/pending";
    }

    // 2. Mostrar formulario para rechazar (Pide el motivo)
    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String rejectOviUserForm(Model model, @PathVariable int id) {
        OviUser oviUser = new OviUser();
        oviUser.setIdOviUser(id);
        model.addAttribute("oviUser", oviUser);
        return "ovi-user/reject";
    }

    // 3. Procesar el rechazo guardando el motivo
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

    // 1. Mostrar la página de confirmación de borrado (GET)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteConfirm(Model model, @PathVariable int id) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(id));
        return "ovi-user/delete";
    }

    // 2. Ejecutar el borrado real tras confirmar (POST)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processDelete(
            @ModelAttribute("oviUser") OviUser oviUser) {
        oviUserDao.deleteOviUser(oviUser.getIdOviUser());
        return "redirect:list";
    }
}
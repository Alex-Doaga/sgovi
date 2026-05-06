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

    // Operación listar todos los oviUsers
    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("oviUsers", oviUserDao.getOviUsers());
        model.addAttribute("currentState", "all");
        return "ovi-user/list";
    }

    // Operación listar los oviUsers por estado
    @RequestMapping("/list/{state}")
    public String listByState(Model model, @PathVariable String state) {
        model.addAttribute("oviUsers", oviUserDao.getOviUsersByState(state)); // O el nombre que tenga tu método en el DAO
        model.addAttribute("currentState", state); // <-- Añadimos esto
        return "ovi-user/list"; // <-- Ahora TODOS devuelven la misma vista
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

    // Procesar el formulario de añadir
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oviUser") OviUser oviUser, BindingResult bindingResult) {

        // 1. Ejecutamos las validaciones normales (OviUserValidator)
        OviUserValidator oviUserValidator = new OviUserValidator();
        oviUserValidator.validate(oviUser, bindingResult);

        // 2. VALIDACIÓN DE DNI DUPLICADO:
        // Solo buscamos en la BD si el usuario ha escrito algo en el DNI y no ha dado error de formato antes
        if (oviUser.getDniNie() != null && !oviUser.getDniNie().trim().isEmpty() && !bindingResult.hasFieldErrors("dniNie")) {
            OviUser existingUser = oviUserDao.getOviUserByDni(oviUser.getDniNie());

            // Si el objeto no es nulo, significa que ya hay alguien con ese DNI en la base de datos
            if (existingUser != null) {
                // Añadimos el error específicamente al campo 'dniNie'
                bindingResult.rejectValue("dniNie", "duplicat", "Aquest DNI/NIE ja està registrat al sistema.");
            }
        }

        // 3. Comprobamos si hay errores (de validación normal o de DNI duplicado)
        if (bindingResult.hasErrors()) {
            return "ovi-user/add"; // Devolvemos el formulario con los errores en rojo
        }

        // Si todo está perfecto y el DNI es nuevo, lo guardamos en la BD
        oviUserDao.addOviUser(oviUser);

        // Cambia la redirección a donde te interese al acabar el registro (ej. login o lista)
        return "redirect:/";
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

    // ==========================================
    //   DASHBOARD
    // ==========================================

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        // Por ahora, como no tenemos login real,
        // buscamos un usuario de prueba (por ejemplo el ID 1)
        // para que la vista tenga datos que mostrar.
        OviUser oviUser = oviUserDao.getOviUser(1);
        model.addAttribute("oviUser", oviUser);

        return "ovi-user/dashboard";
    }
}
package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.OviUserDao;
import es.uji.ei1027.sgovi.dao.RequestDao;
import es.uji.ei1027.sgovi.modelo.PACandidate;
import es.uji.ei1027.sgovi.modelo.OviUser;
import jakarta.servlet.http.HttpSession;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import es.uji.ei1027.sgovi.modelo.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/ovi-user")
public class OviUserController {

    private OviUserDao oviUserDao;
    private int pageLength = 10;

    private RequestDao requestDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

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
    public String list(Model model, @RequestParam("page") Optional<Integer> page) {
        List<OviUser> oviUsers = oviUserDao.getOviUsers();
        Paginador.paginate(model, oviUsers, page, pageLength, "oviUsersPaged");

        model.addAttribute("currentState", "all");
        return "ovi-user/list";
    }

    // Operación listar los oviUsers por estado
    @RequestMapping("/list/{state}")
    public String listByState(Model model, @PathVariable String state, @RequestParam("page") Optional<Integer> page) {
        List<OviUser> oviUsers = oviUserDao.getOviUsersByState(state);
        Paginador.paginate(model, oviUsers, page, pageLength, "oviUsersPaged");
        model.addAttribute("currentState", state);
        return "ovi-user/list";
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
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(oviUser.getPassword());
        oviUser.setPassword(encryptedPassword);

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
    @RequestMapping(value = "/accept/{id}", method = RequestMethod.GET)
    public String acceptOviUser(@PathVariable int id) {
        oviUserDao.updateOviUserState(id, "accepted", null);
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

    // Vista de confirmación antes de aceptar
    @RequestMapping(value = "/accept/confirm/{id}", method = RequestMethod.GET)
    public String confirmAcceptOviUser(Model model, @PathVariable int id) {
        model.addAttribute("oviUser", oviUserDao.getOviUser(id));
        return "ovi-user/confirm-accept";
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
    //   CANDIDATOS SOLICITUD OVI USER
    // ==========================================


    // Listar los candidatos de una solicitud aceptada
    @RequestMapping("/candidates/list/{requestId}")
    public String listCandidates(Model model, @PathVariable int requestId) {
        System.out.println("REQUEST ID listCandidates " + requestId);
        model.addAttribute("currentState", "all");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesForRequest(requestId);
        model.addAttribute("candidates", candidates);

        return "request/candidates";
    }

    // ==========================================
    //   DASHBOARD
    // ==========================================

    @RequestMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        UserDetails user = (UserDetails) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // 3. ¡SOLUCIÓN! En lugar de cast, buscamos el perfil completo por email
        // Usamos el email que viene en el objeto UserDetails de la sesión
        OviUser oviUser = oviUserDao.getOviUserByEmail(user.getEmail());
        model.addAttribute("oviUser", oviUser);

        return "ovi-user/dashboard";
    }

    // Ver perfil de un usuario OVI
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewOviUser(Model model, @PathVariable int id, @RequestParam(value="review", defaultValue="false") boolean review) {
        OviUser oviUser = oviUserDao.getOviUser(id); // O como se llame tu método en el DAO
        model.addAttribute("oviUser", oviUser);

        // Si necesitas pasar una bandera para activar los botones de acción:
        model.addAttribute("isReviewMode", review);

        return "ovi-user/view";
    }
}
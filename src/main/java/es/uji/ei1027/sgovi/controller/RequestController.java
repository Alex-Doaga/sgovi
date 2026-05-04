package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.RequestDao;
import es.uji.ei1027.sgovi.modelo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    // Operaciones: listar, crear, actualizar, borrar

    // ==========================================
    //   LISTAR REQUESTS
    // ============================================

    // Listar TODAS las solicitudes del usuario (sin filtrar)
    @RequestMapping("/list/user/{id}")
    public String listUserRequests(Model model, @PathVariable int id) {
        model.addAttribute("requests", requestDao.getRequestsByOviUser(id));
        model.addAttribute("userId", id); // Lo pasamos para los botones
        model.addAttribute("currentState", "all"); // Indicamos que estamos en "Todas"
        return "request/list";
    }

    // Listar solicitudes del usuario filtradas por estado (pending, accepted, refused)
    @RequestMapping("/list/user/{id}/{state}")
    public String listUserRequestsByState(Model model, @PathVariable int id, @PathVariable String state) {
        model.addAttribute("requests", requestDao.getRequestsByOviUserAndState(id, state));
        model.addAttribute("userId", id); // Lo pasamos para los botones
        model.addAttribute("currentState", state); // Indicamos la pestaña activa
        return "request/list";
    }

    // ==========================================
    //   VER DETALLE DE REQUEST
    // ==========================================

    //Mostrat una request
    @RequestMapping("/view/{id}")
    public String viewRequest(Model model, @PathVariable int id) {
        Request request = requestDao.getRequest(id);
        model.addAttribute("request", request);
        return "request/view";
    }

    // ==========================================
    //   CREAR/AÑADIR Request
    // ==========================================

    // Mostrar formulario de añadir
    @RequestMapping(value = "/add/{idUser}", method = RequestMethod.GET)
    public String addRequest(Model model, @PathVariable int idUser) {
        Request request = new Request();
        request.setOviUserId(idUser); // Vinculamos la solicitud al usuario
        model.addAttribute("request", request);
        return "request/add";
    }

    // Procesar el formulario de añadir
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request, BindingResult bindingResult) {

        // 1. Instanciar y ejecutar el validador
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, bindingResult);

        // 2. Comprobar si hay errores
        if (bindingResult.hasErrors()) {
            // Si hay errores, devolvemos la misma vista del formulario para que muestre los mensajes
            return "request/add";
        }

        // 3. Si todo está correcto, asignamos la fecha actual y guardamos en BD
        request.setRequestDate(LocalDate.now());

        requestDao.addRequest(request);
        return "redirect:/request/list/user/" + request.getOviUserId();
    }
}
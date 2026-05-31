package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.MessageDao;
import es.uji.ei1027.sgovi.dao.NegotiationDao;
import es.uji.ei1027.sgovi.dao.PaDao;
import es.uji.ei1027.sgovi.dao.RequestDao;
import es.uji.ei1027.sgovi.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/request")
public class RequestController {

    private RequestDao requestDao;
    private int pageLength = 5;

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    // Operaciones: listar, crear, actualizar, borrar

    // ==========================================
    //   LISTAR REQUESTS
    // ============================================

    // Listar TODAS las solicitudes del usuario (sin filtrar)
//    @RequestMapping("/list/user/{id}")
//    public String listUserRequests(Model model, @PathVariable int id) {
//        model.addAttribute("requests", requestDao.getRequestsByOviUser(id));
//        model.addAttribute("userId", id); // Lo pasamos para los botones
//        model.addAttribute("currentState", "all"); // Indicamos que estamos en "Todas"
//        return "request/list";
//    }
    @RequestMapping("/list/user/{id}")
    public String listUserRequests(Model model, @PathVariable int id,
                                   @RequestParam("page") Optional<Integer> page) {
        List<Request> requests = requestDao.getRequestsByOviUser(id);
        Paginador.paginate(model, requests, page, pageLength, "requestsPaged");

        model.addAttribute("userId", id);
        model.addAttribute("currentState", "all");
        return "request/list";
    }
    // Listar solicitudes del usuario filtradas por estado (pending, accepted, refused)
//    @RequestMapping("/list/user/{id}/{state}")
//    public String listUserRequestsByState(Model model, @PathVariable int id, @PathVariable String state) {
//        model.addAttribute("requests", requestDao.getRequestsByOviUserAndState(id, state));
//        model.addAttribute("userId", id); // Lo pasamos para los botones
//        model.addAttribute("currentState", state); // Indicamos la pestaña activa
//        return "request/list";
//    }
    @RequestMapping("/list/user/{id}/{state}")
    public String listUserRequestsByState(Model model, @PathVariable int id,
                                          @PathVariable String state,
                                          @RequestParam("page") Optional<Integer> page) {
        List<Request> requests = requestDao.getRequestsByOviUserAndState(id, state);
        Paginador.paginate(model, requests, page, pageLength, "requestsPaged");

        model.addAttribute("requests", requestDao.getRequestsByOviUserAndState(id, state));

        model.addAttribute("userId", id);
        model.addAttribute("currentState", state);
        return "request/list";
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

    // ==========================================
//   ACCIONES DE REVISIÓN TÉCNICA (Aceptar / Rechazar)
// ==========================================

    // 1. Ver detalle de la solicitud en modo revisión
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewRequest(Model model, @PathVariable int id,
                              @RequestParam(value="review", defaultValue="false") boolean review) {
        Request request = requestDao.getRequest(id); // Asumiendo que existe getRequest en tu DAO
        model.addAttribute("request", request);

        // Activamos el modo revisión si viene el parámetro 'review' y la solicitud está pendiente
        boolean isReviewMode = review && "PENDING".equalsIgnoreCase(String.valueOf(request.getState()));
        model.addAttribute("isReviewMode", isReviewMode);

        return "request/view";
    }

    // 2. Mostrar pantalla de confirmación antes de aceptar
    @RequestMapping(value = "/accept/confirm/{id}", method = RequestMethod.GET)
    public String confirmAcceptRequest(Model model, @PathVariable int id) {
        Request request = requestDao.getRequest(id);
        model.addAttribute("request", request);
        return "request/confirm-accept";
    }

    // 3. Procesar la aceptación definitiva (POST)
    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST)
    public String processAcceptRequest(@PathVariable int id) {
        // Actualizamos el estado a 'accepted'. Ajusta el método según tu RequestDao
        requestDao.updateRequestState(id, "accepted", null);
        return "redirect:/technical/list-requests/state/pending";
    }

    // 4. Mostrar formulario de rechazo
    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String rejectRequest(Model model, @PathVariable int id) {
        Request request = requestDao.getRequest(id);
        model.addAttribute("request", request);
        return "request/reject";
    }

    // 5. Procesar el formulario de rechazo (POST)
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String processRejectSubmit(@ModelAttribute("request") Request request) {
        // Guardamos el motivo del rechazo en el campo 'comments' tal como pide list-requests.html
        requestDao.updateRequestState(
                request.getIdRequest(),
                "refused",
                request.getComments()
        );
        return "redirect:/technical/list-requests/state/pending";
    }



    // ==========================================
    //   CANDIDATOS SOLICITUD OVI USER
    // ==========================================

    // Listar los candidatos de una solicitud aceptada
    @RequestMapping("/candidates/list/{requestId}")
    public String listCandidates(Model model, @PathVariable int requestId) {
        model.addAttribute("currentState", "all");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesForRequest(requestId);
        model.addAttribute("candidates", candidates);

        return "request/candidates";
    }

    // Listar los candidatos que no tienen una negociación
    @RequestMapping("/candidates/withoutNegotiation/{requestId}")
    public String listCandidatesWithoutNegotiation(Model model, @PathVariable int requestId) {

        model.addAttribute("currentState", "withoutNegotiation");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesWithoutNegotiation(requestId);
        model.addAttribute("candidates", candidates);

        return "request/candidates";
    }

    // Listar los candidatos que tienen una negociación abierta
    @RequestMapping("/candidates/withNegotiation/{requestId}")
    public String listCandidatesWithNegotiation(Model model, @PathVariable int requestId) {

        model.addAttribute("currentState", "withNegotiation");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesWithNegotiation(requestId);
        model.addAttribute("candidates", candidates);

        return "request/candidates";
    }

    // Listar los candidatos que tienen contrato
    @RequestMapping("/candidates/withContract/{requestId}")
    public String listCandidatesWithContract(Model model, @PathVariable int requestId) {

        model.addAttribute("currentState", "withContract");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesWithNegotiationAndContract(requestId);
        model.addAttribute("candidates", candidates);

        return "request/candidates";
    }
}


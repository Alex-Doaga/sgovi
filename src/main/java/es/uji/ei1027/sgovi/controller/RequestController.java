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

    // ==========================================
    //   LISTAR REQUESTS
    // ==========================================

    @RequestMapping("/list/user/{id}")
    public String listUserRequests(Model model, @PathVariable int id,
                                   @RequestParam("page") Optional<Integer> page) {
        List<Request> requests = requestDao.getRequestsByOviUser(id);
        Paginador.paginate(model, requests, page, pageLength, "requestsPaged");

        model.addAttribute("userId", id);
        model.addAttribute("currentState", "all");
        return "request/list";
    }

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
<<<<<<< HEAD

=======
    
>>>>>>> origin/master
    // ==========================================
    //   CREAR/AÑADIR Request
    // ==========================================

    @RequestMapping(value = "/add/{idUser}", method = RequestMethod.GET)
    public String addRequest(Model model, @PathVariable int idUser) {
        Request request = new Request();
        request.setOviUserId(idUser);
        model.addAttribute("request", request);
        return "request/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("request") Request request, BindingResult bindingResult) {
        RequestValidator requestValidator = new RequestValidator();
        requestValidator.validate(request, bindingResult);

        if (bindingResult.hasErrors()) {
            return "request/add";
        }

        request.setRequestDate(LocalDate.now());
        requestDao.addRequest(request);
        return "redirect:/request/list/user/" + request.getOviUserId();
    }

    // ==========================================
    //   ACCIONES DE REVISIÓN TÉCNICA Y DETALLE
    // ==========================================

    // Se mantiene esta versión que unifica la vista normal y la de revisión
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
<<<<<<< HEAD
    public String viewRequest(Model model, @PathVariable int id,
                              @RequestParam(value="review", defaultValue="false") boolean review) {
        Request request = requestDao.getRequest(id);
=======
    public String viewRequest(Model model, @PathVariable int id, @RequestParam(value="review", defaultValue="false") boolean review) {
        Request request = requestDao.getRequest(id); // Asumiendo que existe getRequest en tu DAO
>>>>>>> origin/master
        model.addAttribute("request", request);

        // Activamos el modo revisión si viene el parámetro 'review' y la solicitud está pendiente
        boolean isReviewMode = review && "PENDING".equalsIgnoreCase(String.valueOf(request.getState()));
        model.addAttribute("isReviewMode", isReviewMode);

        return "request/view";
    }

    @RequestMapping(value = "/accept/confirm/{id}", method = RequestMethod.GET)
    public String confirmAcceptRequest(Model model, @PathVariable int id) {
        Request request = requestDao.getRequest(id);
        model.addAttribute("request", request);
        return "request/confirm-accept";
    }

    @RequestMapping(value = "/accept/{id}", method = RequestMethod.POST)
    public String processAcceptRequest(@PathVariable int id) {
        requestDao.updateRequestState(id, "accepted", null);
        return "redirect:/technical/list-requests/state/pending";
    }

    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String rejectRequest(Model model, @PathVariable int id) {
        Request request = requestDao.getRequest(id);
        model.addAttribute("request", request);
        return "request/reject";
    }

    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String processRejectSubmit(@ModelAttribute("request") Request request) {
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

    @RequestMapping("/candidates/list/{requestId}")
    public String listCandidates(Model model, @PathVariable int requestId) {
        model.addAttribute("currentState", "all");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesForRequest(requestId);
        model.addAttribute("candidates", candidates);
        return "request/candidates";
    }

    @RequestMapping("/candidates/withoutNegotiation/{requestId}")
    public String listCandidatesWithoutNegotiation(Model model, @PathVariable int requestId) {
        model.addAttribute("currentState", "withoutNegotiation");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesWithoutNegotiation(requestId);
        model.addAttribute("candidates", candidates);
        return "request/candidates";
    }

    @RequestMapping("/candidates/withNegotiation/{requestId}")
    public String listCandidatesWithNegotiation(Model model, @PathVariable int requestId) {
        model.addAttribute("currentState", "withNegotiation");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesWithNegotiation(requestId);
        model.addAttribute("candidates", candidates);
        return "request/candidates";
    }

    @RequestMapping("/candidates/withContract/{requestId}")
    public String listCandidatesWithContract(Model model, @PathVariable int requestId) {
        model.addAttribute("currentState", "withContract");
        model.addAttribute("requestId", requestId);
        List<PACandidate> candidates = requestDao.findCandidatesWithNegotiationAndContract(requestId);
        model.addAttribute("candidates", candidates);
        return "request/candidates";
    }
}

    public String viewRequest(Model model, @PathVariable int id, @RequestParam(value="review", defaultValue="false") boolean review) {
        Request request = requestDao.getRequest(id); // Asumiendo que existe getRequest en tu DAO
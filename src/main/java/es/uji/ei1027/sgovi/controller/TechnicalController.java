package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.NegotiationDao;
import es.uji.ei1027.sgovi.dao.OviUserDao;
import es.uji.ei1027.sgovi.dao.RequestDao;
import es.uji.ei1027.sgovi.dto.PACandidateDTO;
import es.uji.ei1027.sgovi.modelo.OviUser;
import es.uji.ei1027.sgovi.modelo.PA;
import es.uji.ei1027.sgovi.modelo.Request;
import es.uji.ei1027.sgovi.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/technical")
public class TechnicalController {

    private NegotiationDao negotiationDao;
    private OviUserDao oviUserDao;
    private RequestDao requestDao;
    private int pageLength = 10;

    @Autowired
    public void setNegotiationDao(NegotiationDao negotiationDao) {
        this.negotiationDao = negotiationDao;
    }
    @Autowired
    public void setOviUserDao(OviUserDao oviUserDao) {
        this.oviUserDao = oviUserDao;
    }
    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    // ==========================================
    //  SOLICITUDES ASISTENTES PERSONALES (AP)
    // ==========================================

    // Listar todas las solicitudes
//    @RequestMapping("/list-requests")
//    public String listRequests(Model model) {
//        int id = 1;
//        model.addAttribute("requests", requestDao.getRequests());
//        //model.addAttribute("requests", requestDao.getRequestsByOviUser(id));
//        model.addAttribute("userId", id);
//        // Indicamos que estamos en "Todas las solicitudes"
//        model.addAttribute("currentState", "all");
//        return "technical/list-requests";
//    }
    @RequestMapping("/list-requests")
    public String listRequests(Model model, @RequestParam("page") Optional<Integer> page) {
        int id = 1;
        List<Request> requests = requestDao.getRequests();

        Paginador.paginate(model, requests, page, pageLength, "requestsPaged");

        model.addAttribute("userId", id);
        model.addAttribute("currentState", "all");
        return "technical/list-requests";
    }

    // Listar solicitudes por estado
//    @RequestMapping("/list-requests/state/{state}")
//    public String listByState(Model model, @PathVariable String state) {
//        model.addAttribute("requests", requestDao.getRequestsByState(state)); // O el nombre que tenga tu método en el DAO
//        model.addAttribute("currentState", state);
//        return "technical/list-requests";
//    }
    @RequestMapping("/list-requests/state/{state}")
    public String listByState(Model model, @PathVariable String state, @RequestParam("page") Optional<Integer> page) {
        List<Request> requests = requestDao.getRequestsByState(state);
        Paginador.paginate(model, requests, page, pageLength, "requestsPaged");

        model.addAttribute("currentState", state);
        return "technical/list-requests";
    }

    // Aceptar solicitud
    @RequestMapping("/list-requests/pending/accept/{id}")
    public String acceptRequest(@PathVariable int id) {
        requestDao.updateRequestState(id, "accepted", null);
        return "redirect:/technical/candidates/list/" + id;
    }

    // Rechazar solicitud
    @RequestMapping("/list-requests/pending/refuse/{id}")
    public String rejectRequest(Model model, @PathVariable int id, @RequestParam String reason) {
        requestDao.updateRequestState(id, "refused", reason);
        return "redirect:/technical/list-requests/state/refused";
    }

    // ==========================================
    //   CANDIDATOS
    // ==========================================

    // Listar los candidatos de una solicitud aceptada
//    @RequestMapping("/candidates/list/{requestId}")
//    public String listCandidates(Model model, @PathVariable int requestId) {
//        model.addAttribute("currentState", "all");
//        model.addAttribute("requestId", requestId);
//        List<PA> candidates = requestDao.findCandidatesForRequest(requestId);
//        model.addAttribute("candidates", candidates);
//
//        //TODO Noemí: De alguna forma se debe saber el estado de la negociacion de ese candidato concreto en la tabla
//        for(PA candidate : candidates) {
//            System.out.println(">>>>>>>>>>CANDIDATE  " + candidate);
//        }
//       return "technical/candidates";
//    }
    @RequestMapping("/candidates/list/{requestId}")
    public String listCandidates(Model model, @PathVariable int requestId) {
        System.out.println("REQUEST ID listCandidates " + requestId);
        model.addAttribute("currentState", "all");
        model.addAttribute("requestId", requestId);
        List<PACandidateDTO> candidates = requestDao.findCandidatesForRequest(requestId);
        model.addAttribute("candidates", candidates);

       return "technical/candidates";
    }

    // Listar los candidatos que no tienen un contrato
    @RequestMapping("/candidates/withoutContract/{requestId}")
    public String listCandidatesWithoutContract(Model model, @PathVariable int requestId) {
        System.out.println("REQUEST ID listCandidatesWithoutContract " + requestId);

        model.addAttribute("currentState", "withoutContract");
        model.addAttribute("requestId", requestId);
        List<PACandidateDTO> candidates = requestDao.findCandidatesWithoutContract(requestId);
        model.addAttribute("candidates", candidates);

        return "technical/candidates";
    }

    // Listar los candidatos que tienen contrato
    @RequestMapping("/candidates/withContract/{requestId}")
    public String listCandidatesWithContract(Model model, @PathVariable int requestId) {
        System.out.println("REQUEST ID listCandidatesWithContract " + requestId);

        model.addAttribute("currentState", "withContract");
        model.addAttribute("requestId", requestId);
        List<PACandidateDTO> candidates = requestDao.findCandidatesWithContract(requestId);
        model.addAttribute("candidates", candidates);

        return "technical/candidates";
    }

    //TODO Noemí: de momento es una prueba
    // Visualizar los detalles de una negociación entra el usuario ovi y el candidato PA de una request
    @RequestMapping("/candidates/negotiation-details/{paId}/{requestId}")
    public String listCandidates(Model model, @PathVariable int paId, @PathVariable int requestId) {
        model.addAttribute("currentState", "accepted");
        model.addAttribute("negotiation", negotiationDao.getNegotiationByPaInRequest(paId, requestId));
        return "technical/negotiation-details";
    }

    // ==========================================
    //   DASHBOARD
    // ==========================================

//    @RequestMapping("/dashboard")
//    public String dashboard(Model model) {
//        // Por ahora, como no tenemos login real,
//        // buscamos un usuario de prueba (por ejemplo el ID 1)
//        // para que la vista tenga datos que mostrar.
//        OviUser oviUser = oviUserDao.getOviUser(1);
//        model.addAttribute("oviUser", oviUser);
//
//        return "technical/dashboard";
//    }
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
}
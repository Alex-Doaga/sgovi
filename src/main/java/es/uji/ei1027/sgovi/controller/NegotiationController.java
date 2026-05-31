package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.MessageDao;
import es.uji.ei1027.sgovi.dao.NegotiationDao;
import es.uji.ei1027.sgovi.dao.PaDao;
import es.uji.ei1027.sgovi.dao.RequestDao;
import es.uji.ei1027.sgovi.modelo.*;
import es.uji.ei1027.sgovi.modelo.enums.NegotiationStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/negotiation")
public class NegotiationController {

    private NegotiationDao negotiationDao;
    private MessageDao messageDao;
    private PaDao paDao;
    private RequestDao requestDao;

    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @Autowired
    public void setPaDao(PaDao paDao) {
        this.paDao = paDao;
    }

    @Autowired
    public void setNegotiationDao(NegotiationDao negotiationDao) {
        this.negotiationDao = negotiationDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @RequestMapping("/list/{oviUserId}")
    public String listNegotiations(@PathVariable int oviUserId, Model model) {

        List<PACandidateNegotiation> negotiations = negotiationDao.getNegotiationsByOviUser(oviUserId);

        model.addAttribute("oviUserId", oviUserId);
        model.addAttribute("negotiations", negotiations);

        return "negotiation/list";
    }

    @PostMapping("/start/{requestId}/{paId}")
    public String startNegotiation(@PathVariable int requestId,
                                   @PathVariable int paId) {

        Negotiation negotiation = negotiationDao.getNegotiationByPaInRequest(paId, requestId);

        if (negotiation == null) {
            negotiation = new Negotiation();

            negotiation.setIdRequest(requestId);
            negotiation.setIdPa(paId);
            negotiation.setStartDate(LocalDate.now());
            negotiation.setNegotiationState(NegotiationStateEnum.sinHablar.toString());

            negotiationDao.addNegotiation(negotiation);
        }

        return "redirect:/negotiation/details/" + paId + "/" + requestId;
    }

    // Visualizar los detalles de una negociación entre el usuario ovi y el candidato PA de una request
    @RequestMapping("/details/{paId}/{requestId}")
    public String negotiationDetails(@PathVariable int paId,
                                     @PathVariable int requestId,
                                     Model model) {

        Negotiation negotiation = negotiationDao.getNegotiationByPaInRequest(paId, requestId);
        Request req = requestDao.getRequest(requestId);

        if (negotiation == null) {
            return "redirect:/negotiation/list";
        }

        List<Message> messages = messageDao.getMessagesByNegotiation(negotiation.getIdNegotiation());

        PA pa = paDao.getPA(paId);

        model.addAttribute("negotiation", negotiation);
        model.addAttribute("messages", messages);
        model.addAttribute("pa", pa);
        model.addAttribute("request", req);

        return "negotiation/details";
    }

    // Enviar un mensaje
    @PostMapping("/message")
    public String sendMessage(@RequestParam int idNegotiation,
                              @RequestParam String messageText) {

        Negotiation negotiation = negotiationDao.getNegotiation(idNegotiation);

        if (negotiation == null) {
            return "redirect:/";
        }

        Message message = new Message();

        message.setIdNegotiation(idNegotiation);
        message.setSenderType(Message.SENDER_OVI_USER);
        message.setMessageText(messageText);

        messageDao.addMessage(message);

        negotiationDao.updateNegotiationState(idNegotiation, NegotiationStateEnum.hablando.toString());

        return "redirect:/negotiation/details/" + negotiation.getIdPa() + "/" + negotiation.getIdRequest();
    }

}

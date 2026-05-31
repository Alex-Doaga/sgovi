package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.ContractDao;
import es.uji.ei1027.sgovi.dao.PaDao;
import es.uji.ei1027.sgovi.dao.RequestDao;
import es.uji.ei1027.sgovi.dto.PACandidateDTO;
import es.uji.ei1027.sgovi.modelo.Contract;
import es.uji.ei1027.sgovi.modelo.PA;
import es.uji.ei1027.sgovi.modelo.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractDao contractDao;
    private int pageLength = 10;
    private RequestDao requestDao;
    private PaDao paDao;

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Autowired
    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Autowired
    public void setPaDao(PaDao paDao) {
        this.paDao = paDao;
    }

    // Operaciones: listar, crear, actualizar, borrar

    // ==========================================
    //   LISTAR Contratos
    // ==========================================

    // Listado de todos los contratos
    @RequestMapping("/list")
    public String listContracts(Model model, @RequestParam("page") Optional<Integer> page) {
        List<Contract> contracts = contractDao.getContractsByState("activo");

        Paginador.paginate(model, contracts, page, pageLength, "contractsPaged");

        model.addAttribute("currentState", "activo");
        return "contract/list";
    }

    // Listado de contratos de un usuario específico
    @RequestMapping("/list/user/{id}")
    public String listUserContracts(Model model, @PathVariable int id,
                                    @RequestParam("page") Optional<Integer> page) {
        List<Contract> contracts = contractDao.getContractsByUserId(id);

        Paginador.paginate(model, contracts, page, pageLength, "contractsPaged");

        model.addAttribute("userId", id);
        model.addAttribute("isUserView", true);
        model.addAttribute("paDao", paDao);
        model.addAttribute("currentState", "user");
        return "contract/list";
    }

    // ==========================================
    //   CREAR/AÑADIR Request
    // ==========================================

    // Formulario de añadir contrato por usuario
    @RequestMapping(value = "/add/user/{idUser}", method = RequestMethod.GET)
    public String addContractByUser(Model model, @PathVariable int idUser) {
        List<Request> requests = requestDao.getRequestsWithoutContractByOviUser(idUser);
        Set<PA> candidatePAs = new HashSet<>();
        for (Request req : requests) {
            List<PACandidateDTO> candidates = requestDao.findCandidatesForRequest(req.getIdRequest());
            for (PACandidateDTO cand : candidates) {
                candidatePAs.add(cand.getPa());
            }
        }
        List<PA> pas = new ArrayList<>(candidatePAs);
        model.addAttribute("requests", requests);
        model.addAttribute("pas", pas);
        model.addAttribute("idUser", idUser);
        Contract contract = new Contract();
        contract.setContractState("activo");
        model.addAttribute("contract", contract);
        return "contract/add";
    }

    // Procesar el formulario de añadir por usuario
    @RequestMapping(value = "/add/user", method = RequestMethod.POST)
    public String processAddUserSubmit(@ModelAttribute("contract") Contract contract,
                                       @RequestParam("idUser") int idUser,
                                       BindingResult bindingResult, Model model) {
        ContractValidator validator = new ContractValidator();
        validator.validate(contract, bindingResult);
        if (bindingResult.hasErrors()) {
            List<Request> requests = requestDao.getRequestsWithoutContractByOviUser(idUser);
            Set<PA> candidatePAs = new HashSet<>();
            for (Request req : requests) {
                List<PACandidateDTO> candidates = requestDao.findCandidatesForRequest(req.getIdRequest());
                for (PACandidateDTO cand : candidates) {
                    candidatePAs.add(cand.getPa());
                }
            }
            List<PA> pas = new ArrayList<>(candidatePAs);
            model.addAttribute("requests", requests);
            model.addAttribute("pas", pas);
            model.addAttribute("idUser", idUser);
            return "contract/add";
        }
        contractDao.addContract(contract);
        return "redirect:/contract/list/user/" + idUser;
    }

    // ==========================================
    //   MODIFICAR/ACTUALIZAR Contrato
    // ==========================================

    // MOSTRAR FORMULARIO DE EDITAR (GET)
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String updateContract(Model model, @PathVariable int id) {
        Contract contract = contractDao.getContract(id);
        model.addAttribute("contract", contract);

        Request request = requestDao.getRequest(contract.getIdRequest());
        int idUser = request.getOviUserId();

        model.addAttribute("idUser", idUser);

        return "contract/update";
    }

    // PROCESAR EL FORMULARIO DE EDITAR (POST)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("contract") Contract contract,
                                      BindingResult bindingResult, Model model) {

        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validateUpdate(contract, bindingResult);

        if (bindingResult.hasErrors()) {
            Request request = requestDao.getRequest(contract.getIdRequest());
            model.addAttribute("idUser", request.getOviUserId());
            return "contract/update";
        }

        contractDao.updateContract(contract);

        Request request = requestDao.getRequest(contract.getIdRequest());
        return "redirect:/contract/list/user/" + request.getOviUserId();
    }

}
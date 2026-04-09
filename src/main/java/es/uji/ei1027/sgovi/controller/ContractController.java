package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.ContractDao;
import es.uji.ei1027.sgovi.modelo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractDao contractDao;

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    // Listado de todos los contratos
    @RequestMapping("/list")
    public String listContracts(Model model) {
        model.addAttribute("contracts", contractDao.getContractsByState("activo"));
        return "contract/list";
    }

    // Listado de contratos de un usuario específico (el que llama tu botón)
    @RequestMapping("/list/ovi-user/{id}")
    public String listByOviUser(Model model, @PathVariable int id) {
        // AHORA SÍ busca los contratos de ese usuario concreto
        model.addAttribute("contracts", contractDao.getContractsByOviUser(id));
        model.addAttribute("oviUserId", id);
        return "contract/list";
    }

    // Formulario de añadir contrato
    @RequestMapping(value = "/add/{idRequest}", method = RequestMethod.GET)
    public String addContract(Model model, @PathVariable int idRequest) {
        Contract contract = new Contract();
        contract.setIdRequest(idRequest);
        contract.setContractState("activo"); // Estado inicial por defecto
        model.addAttribute("contract", contract);
        return "contract/add";
    }

    // Procesar el formulario de añadir
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(@ModelAttribute("contract") Contract contract) {
        contractDao.addContract(contract);
        return "redirect:/contract/list";
    }

    // Eliminar contrato
    @RequestMapping(value = "/delete/{id}")
    public String processDelete(@PathVariable int id) {
        contractDao.deleteContract(id);
        return "redirect:/contract/list";
    }

    // Mostrar formulario de edición
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable int id) {
        model.addAttribute("contract", contractDao.getContract(id));
        return "contract/update";
    }

    // Procesar el formulario de edición
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("contract") Contract contract) {
        contractDao.updateContract(contract);
        // Te redirige a la lista general (puedes ajustar esta ruta si lo necesitas)
        return "redirect:/contract/list";
    }

    // Mostrar página de confirmación de borrado
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteContract(Model model, @PathVariable int id) {
        model.addAttribute("contract", contractDao.getContract(id));
        return "contract/delete";
    }

    // Procesar el borrado
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processDeleteSubmit(@ModelAttribute("contract") Contract contract) {
        contractDao.deleteContract(contract.getIdContract());
        return "redirect:/contract/list";
    }

}
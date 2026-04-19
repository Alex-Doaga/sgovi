package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.ContractDao;
import es.uji.ei1027.sgovi.modelo.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractDao contractDao;

    @Autowired
    public void setContractDao(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    // Operaciones: listar, crear, actualizar, borrar

    // ==========================================
    //   LISTAR Contratos
    // ==========================================

    // Listado de todos los contratos
    @RequestMapping("/list")
    public String listContracts(Model model) {
        model.addAttribute("contracts", contractDao.getContractsByState("activo"));
        return "contract/list";
    }

    // Listado de contratos de un usuario específico
    @RequestMapping("/list/user/{id}")
    public String listUserContracts(Model model, @PathVariable int id) {
        model.addAttribute("contracts", contractDao.getContractsByUserId(id));
        return "contract/list";
    }

    // ==========================================
    //   CREAR/AÑADIR Request
    // ==========================================

    // Formulario de añadir contrato
    @RequestMapping(value = "/add/{idRequest}", method = RequestMethod.GET)
    public String addContract(Model model, @PathVariable int idRequest) {
        Contract contract = new Contract();
        contract.setIdRequest(idRequest);
        contract.setContractState("activo"); // Estado por defecto
        model.addAttribute("contract", contract);
        return "contract/add";
    }

    // Procesar el formulario de añadir con VALIDACIÓN
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("contract") Contract contract,
                                   BindingResult bindingResult) {

        // 1. Instanciar y ejecutar el validador
        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);

        // 2. Si hay errores, volvemos a la vista del formulario
        if (bindingResult.hasErrors()) {
            return "contract/add";
        }

        // 3. Si no hay errores, guardamos
        contractDao.addContract(contract);
        return "redirect:/contract/list/ovi-user/" + contract.getIdRequest();
    }

    // ==========================================
    //   MODIFICAR/ACTUALIZAR Contrato
    // ==========================================

    // Mostrar formulario de edición
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String editContract(Model model, @PathVariable int id) {
        model.addAttribute("contract", contractDao.getContract(id));
        return "contract/update";
    }

    // Procesar el formulario de edición con VALIDACIÓN
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("contract") Contract contract,
                                      BindingResult bindingResult) {

        // 1. Instanciar y ejecutar el validador (reutilizamos el mismo)
        ContractValidator contractValidator = new ContractValidator();
        contractValidator.validate(contract, bindingResult);

        // 2. Si hay errores en la edición, volvemos a la vista de update
        if (bindingResult.hasErrors()) {
            return "contract/update";
        }

        // 3. Si todo es correcto, actualizamos
        contractDao.updateContract(contract);
        return "redirect:/contract/list";
    }

    // ==========================================
    //   BORRADO DE CONTRATOS
    // ==========================================

    // Eliminar contrato
    @RequestMapping(value = "/delete/{id}")
    public String processDelete(@PathVariable int id) {
        contractDao.deleteContract(id);
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
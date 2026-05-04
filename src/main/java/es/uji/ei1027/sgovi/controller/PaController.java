package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.PaDao;
import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/pa")
public class PaController {

    private PaDao paDao;

    @Autowired
    public void setPaDao(PaDao paDao) {
        this.paDao = paDao;
    }

    // ==========================================
    //   CREAR / AÑADIR PA
    // ==========================================

    // 1. Método GET para mostrar el formulario
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addPa(Model model) {
        // Aquí es donde le enviamos el objeto "pa" vacío para que los errores desaparezcan
        model.addAttribute("pa", new PA());
        return "pa/add";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("pa") PA pa, BindingResult bindingResult) {

        // 1. Ejecutamos las validaciones normales (campos vacíos, mayor de edad, etc.)
        PaValidator paValidator = new PaValidator();
        paValidator.validate(pa, bindingResult);

        // 2. VALIDACIÓN DE DNI DUPLICADO:
        // Solo buscamos en la BD si el usuario ha escrito algo en el DNI y no ha dado error de formato antes
        if (pa.getDniNie() != null && !pa.getDniNie().trim().isEmpty() && !bindingResult.hasFieldErrors("dniNie")) {
            PA existingPa = paDao.getPaByDni(pa.getDniNie());

            // Si el objeto no es nulo, significa que ya hay alguien con ese DNI en la base de datos
            if (existingPa != null) {
                // Añadimos el error específicamente al campo 'dniNie'
                bindingResult.rejectValue("dniNie", "duplicat", "Aquest DNI/NIE ja està registrat al sistema.");
            }
        }

        // 3. Comprobamos si el validador o nuestra comprobación de DNI han encontrado algún error
        if (bindingResult.hasErrors()) {
            return "pa/add"; // Devolvemos el formulario con los errores en rojo
        }

        // Si todo está perfecto y el DNI es nuevo, lo guardamos en la BD
        paDao.addPA(pa);

        return "redirect:/pa/list";
    }

    // ==========================================
    //   LISTAR PA (ASISTENTES PERSONALES)
    // ==========================================

    // 1. Ruta principal: Si alguien entra a /pa/list, lo redirigimos a los pendientes por defecto
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String listPasDefault() {
        return "redirect:/pa/list/pending";
    }

    // 2. Ruta con filtro: Carga la lista según la pestaña seleccionada (pending, accepted, refused)
    @RequestMapping(value="/list/{state}", method = RequestMethod.GET)
    public String listPasByState(@PathVariable String state, Model model) {
        // Usamos el método de tu DAO que filtra por estado
        model.addAttribute("pas", paDao.getPasByState(state));

        // Le pasamos el estado actual a la vista para que pinte la pestaña del color correcto
        model.addAttribute("currentState", state);

        // Devolvemos la vista list.html
        return "pa/list";
    }

    // ==========================================
    //   GESTIÓN DE ESTADOS (ACEPTAR/RECHAZAR)
    // ==========================================

    // 1. Aceptar un PA
    @RequestMapping(value = "/accept/{id}")
    public String acceptPa(@PathVariable int id) {
        paDao.updatePaState(id, "accepted", null);
        return "redirect:/pa/list/pending";
    }

    // 2. Mostrar formulario de rechazo
    @RequestMapping(value = "/reject/{id}", method = RequestMethod.GET)
    public String rejectPa(Model model, @PathVariable int id) {
        model.addAttribute("pa", paDao.getPA(id));
        return "pa/reject";
    }

    // 3. Procesar el formulario de rechazo
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public String processRejectSubmit(@ModelAttribute("pa") PA pa) {
        paDao.updatePaState(
                pa.getIdPa(),
                "refused",
                pa.getRejectionReason()
        );
        return "redirect:/pa/list/pending";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        // Obtenemos un Asistente Personal de prueba (ej. ID 1) temporalmente
        PA pa = paDao.getPA(1);
        model.addAttribute("pa", pa);

        return "pa/dashboard";
    }
}
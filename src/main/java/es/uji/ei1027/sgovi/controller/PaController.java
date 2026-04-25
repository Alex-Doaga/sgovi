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
    public void setPaDao(PaDao paDao) { // He corregido el nombre aquí (antes ponía setOviUserDao)
        this.paDao = paDao;
    }

    // 1. Método GET para mostrar el formulario
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addPa(Model model) {
        // Aquí es donde le enviamos el objeto "pa" vacío para que los errores desaparezcan
        model.addAttribute("pa", new PA());
        return "pa/add";
    }

    // 2. Método POST para recoger los datos cuando el usuario le da al botón "Registrar"
    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("pa") PA pa, BindingResult bindingResult) {
        // Si hay errores en el formulario, volvemos a mostrar la vista
        if (bindingResult.hasErrors()) {
            return "pa/add";
        }

        // Si todo va bien, añadimos el PA a la base de datos a través del DAO
        paDao.addPA(pa);

        // Redirigimos a la lista de Asistentes Personales (asegúrate de tener este método list luego)
        return "redirect:/pa/list";
    }

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        // Obtenemos un Asistente Personal de prueba (ej. ID 1) temporalmente
        PA pa = paDao.getPA(1);
        model.addAttribute("pa", pa);

        return "pa/dashboard";
    }
}
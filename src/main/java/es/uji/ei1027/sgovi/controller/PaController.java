package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.PaDao;
import es.uji.ei1027.sgovi.modelo.PA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pa")
public class PaController {

    @Autowired
    private PaDao paDao; // Asegúrate de tener tu DAO inyectado

    @RequestMapping("/dashboard")
    public String dashboard(Model model) {
        // Obtenemos un Asistente Personal de prueba (ej. ID 1) temporalmente
        PA pa = paDao.getPA(1);
        model.addAttribute("pa", pa);

        return "pa/dashboard";
    }
}

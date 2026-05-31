package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.ActivityDao;
import es.uji.ei1027.sgovi.dao.InscriptionDao;
import es.uji.ei1027.sgovi.modelo.*;
import es.uji.ei1027.sgovi.validadores.ActivityValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import es.uji.ei1027.sgovi.dao.UserDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import es.uji.ei1027.sgovi.modelo.UserDetails;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    private ActivityDao activityDao;
    private int pageLength = 5;
    private UserDao userDao;
    private InscriptionDao inscriptionDao;

    @Autowired
    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @Autowired
    public void setInscriptionDao(InscriptionDao inscriptionDao) {
        this.inscriptionDao = inscriptionDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    @RequestMapping("/list")
    public String listActivities(Model model, @RequestParam("page") Optional<Integer> page, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");

        List<Activity> activities = activityDao.getActivities();
        Paginador.paginate(model, activities, page, pageLength, "activitiesPaged");

        if ("Tecnico".equals(user.getRol())) {
            return "activity//list-tecnico";
        }
        return "activity/list";

    }
    @RequestMapping("/join/{id}")
    public String joinActivity(@PathVariable int id, HttpSession session) {
        UserDetails user = (UserDetails) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Inscription inscription = new Inscription();
        inscription.setIdActivity(id);
        inscription.setDate(LocalDate.now());


        if ("OviUser".equals(user.getRol())) {

            inscription.setIdOviUser(1);
            inscription.setIdPa(null);
        } else if ("PA".equals(user.getRol())) {
            inscription.setIdPa(1);
            inscription.setIdOviUser(null);
        }

        inscriptionDao.addInscription(inscription);
        return "redirect:/activity/list";
    }
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("activity", new Activity());
        return "activity/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(@ModelAttribute("activity") Activity activity,
                             BindingResult bindingResult) {
        // Seguimos tu ejemplo: instanciar y validar manualmente
        ActivityValidator activityValidator = new ActivityValidator();
        activityValidator.validate(activity, bindingResult);

        if (bindingResult.hasErrors())
            return "activity/add";

        activityDao.addActivity(activity);
        return "redirect:list";
    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("activity", activityDao.getActivity(id));
        return "activity/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdate(@ModelAttribute("activity") Activity activity,
                                BindingResult bindingResult) {
        // Validamos también en el update como en los ejemplos de clase
        ActivityValidator activityValidator = new ActivityValidator();
        activityValidator.validate(activity, bindingResult);

        if (bindingResult.hasErrors())
            return "activity/update";

        activityDao.updateActivity(activity);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String processDelete(@PathVariable int id) {
        activityDao.deleteActivity(id);
        return "redirect:../list";
    }

    @RequestMapping("/certificado/{id}")
    public String showCertificate(Model model, @PathVariable int id) {
        Activity activity = activityDao.getActivity(id);

        model.addAttribute("activity", activity);
        return "activity/certificado";
    }
}


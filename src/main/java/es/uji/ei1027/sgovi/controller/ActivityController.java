package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.ActivityDao;
import es.uji.ei1027.sgovi.modelo.Activity;
import es.uji.ei1027.sgovi.modelo.Instructor;
import es.uji.ei1027.sgovi.validadores.ActivityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    private ActivityDao activityDao;
    private int pageLength = 10;

    private void preparePagination(Model model, List<Activity> activities, Optional<Integer> page) {
        ArrayList<ArrayList<Activity>> activitiesPaged = new ArrayList<>();
        if (!activities.isEmpty()) {
            int ini = 0;
            while (ini < activities.size()) {
                int fin = Math.min(ini + pageLength, activities.size());
                activitiesPaged.add(new ArrayList<>(activities.subList(ini, fin)));
                ini += pageLength;
            }
        }
        model.addAttribute("activitiesPaged", activitiesPaged);

        int totalPages = activitiesPaged.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("selectedPage", page.orElse(0));
    }

    @Autowired
    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @RequestMapping("/list")
    public String listActivities(Model model, @RequestParam("page") Optional<Integer> page){
        List<Activity> activities = activityDao.getActivities();
        preparePagination(model, activities, page);
        return "activity/list";
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
}

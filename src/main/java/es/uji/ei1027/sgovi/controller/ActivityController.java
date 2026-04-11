package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.ActivityDao;
import es.uji.ei1027.sgovi.modelo.Activity;
import es.uji.ei1027.sgovi.modelo.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/activity")
public class ActivityController {
    private ActivityDao activityDao;
    @Autowired
    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    @RequestMapping("/list")
    public String listActivities(Model model){
        model.addAttribute("activities",activityDao.getActivities());
        return "activity/list";
    }
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("activity", new Activity());
        return "activity/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(
            @ModelAttribute("activity") Activity activity,
            BindingResult bindingResult) {
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
    public String processUpdate(
            @ModelAttribute("activity") Activity activity,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "activity/update";
        activityDao.updateActivity(activity);
        return "redirect:list";
    }
}

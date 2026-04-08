package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.InstructorDao;
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
@RequestMapping("/instructor")
public class InstructorController {

    private InstructorDao instructorDao;

    @Autowired
    public void setInstructorDao(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("instructors", instructorDao.getInstructors());
        return "instructor/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(
            @ModelAttribute("instructor") Instructor instructor,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "instructor/add";
        instructorDao.addInstructor(instructor);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("instructor", instructorDao.getInstructor(id));
        return "instructor/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdate(
            @ModelAttribute("instructor") Instructor instructor,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "instructor/update";
        instructorDao.updateInstructor(instructor);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteConfirm(Model model, @PathVariable int id) {
        model.addAttribute("instructor", instructorDao.getInstructor(id));
        return "instructor/delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String processDelete(
            @ModelAttribute("instructor") Instructor instructor) {
        instructorDao.deleteInstructor(instructor.getIdInstructor());
        return "redirect:list";
    }
}
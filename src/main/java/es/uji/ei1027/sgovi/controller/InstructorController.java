package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.dao.InstructorDao;
import es.uji.ei1027.sgovi.modelo.Instructor;
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
@RequestMapping("/instructor")
public class InstructorController {

    private void preparePagination(Model model, List<Instructor> instructors, Optional<Integer> page) {
        ArrayList<ArrayList<Instructor>> instructorsPaged = new ArrayList<>();
        if (!instructors.isEmpty()) {
            int ini = 0;
            while (ini < instructors.size()) {
                int fin = Math.min(ini + pageLength, instructors.size());
                instructorsPaged.add(new ArrayList<>(instructors.subList(ini, fin)));
                ini += pageLength;
            }
        }
        model.addAttribute("instructorsPaged", instructorsPaged);

        int totalPages = instructorsPaged.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("selectedPage", page.orElse(0));
    }
    private InstructorDao instructorDao;
    private int pageLength = 10;

    @Autowired
    public void setInstructorDao(InstructorDao instructorDao) {
        this.instructorDao = instructorDao;
    }

    @RequestMapping("/list")
    public String list(Model model, @RequestParam("page") Optional<Integer> page) {
        List<Instructor> instructors = instructorDao.getInstructors();
        Paginador.paginate(model, instructors, page, pageLength, "instructorsPaged");

        return "instructor/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor/add";
    }

    // AÑADIDO: Llamada al validador antes de comprobar errores
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAdd(
            @ModelAttribute("instructor") Instructor instructor,
            BindingResult bindingResult) {

        // 1. Validar
        InstructorValidator instructorValidator = new InstructorValidator();
        instructorValidator.validate(instructor, bindingResult);

        // 2. Comprobar errores
        if (bindingResult.hasErrors())
            return "instructor/add";

        // 3. Guardar
        instructorDao.addInstructor(instructor);
        return "redirect:list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("instructor", instructorDao.getInstructor(id));
        return "instructor/update";
    }

    // AÑADIDO: Llamada al validador antes de comprobar errores
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdate(
            @ModelAttribute("instructor") Instructor instructor,
            BindingResult bindingResult) {

        // 1. Validar
        InstructorValidator instructorValidator = new InstructorValidator();
        instructorValidator.validate(instructor, bindingResult);

        // 2. Comprobar errores
        if (bindingResult.hasErrors())
            return "instructor/update";

        // 3. Guardar
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
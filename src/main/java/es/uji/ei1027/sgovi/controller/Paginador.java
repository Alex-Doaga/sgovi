package es.uji.ei1027.sgovi.controller;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Paginador {
    public static <T> void paginate(Model model, List<T> dataList, Optional<Integer> pageParam, int pageLength, String attributeName) {

        // Pas 1: Trocear la lista en sublistas
        ArrayList<ArrayList<T>> pagedList = new ArrayList<>();
        if (dataList != null && !dataList.isEmpty()) {
            int ini = 0;
            while (ini < dataList.size()) {
                int fin = Math.min(ini + pageLength, dataList.size());
                pagedList.add(new ArrayList<>(dataList.subList(ini, fin)));
                ini += pageLength;
            }
        }
        model.addAttribute(attributeName, pagedList);

        // Pas 2: Calcular los números de página para la barra de navegación
        int totalPages = pagedList.size();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // Pas 3: Guardar la página seleccionada (por defecto 0)
        model.addAttribute("selectedPage", pageParam.orElse(0));
    }
}

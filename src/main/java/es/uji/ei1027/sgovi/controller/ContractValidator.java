package es.uji.ei1027.sgovi.controller;

import es.uji.ei1027.sgovi.modelo.Contract;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ContractValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Contract.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contract contract = (Contract) target;

        // Validar que el ID del PA no sea nulo ni negativo
        if (contract.getIdPa() == null || contract.getIdPa() <= 0) {
            errors.rejectValue("idPa", "obligatori", "Cal introduir un ID d'assistent vàlid (mínim 1)");
        }

        // Validar que la fecha de inicio no esté vacía
        if (contract.getStartDate() == null) {
            errors.rejectValue("startDate", "obligatori", "La data d'inici és obligatòria");
        }

        // Validación extra: Si hay fecha de fin, debe ser posterior a la de inicio
        if (contract.getStartDate() != null && contract.getEndDate() != null) {
            if (contract.getEndDate().isBefore(contract.getStartDate())) {
                errors.rejectValue("endDate", "incoherent", "La data de finalització no pot ser anterior a la d'inici");
            }
        }
    }
}
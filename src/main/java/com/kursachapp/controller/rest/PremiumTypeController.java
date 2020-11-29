package com.kursachapp.controller.rest;

import com.kursachapp.domain.entity.PremiumType;
import com.kursachapp.repository.PremiumTypesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class PremiumTypeController {

    @Autowired
    private PremiumTypesRepository premiumTypesRepository;

    @RequestMapping(path = "/premiumType", method = RequestMethod.POST)
    public PremiumType save(
            @RequestParam(name = "numberOfYears") int numberOfYears,
            @RequestParam(name = "accrualToSalary") float accrualToSalary,
            @RequestParam(name = "accrualToVacation") float accrualToVacation) {

        PremiumType premiumType = new PremiumType();

        premiumType.setNumberOfYears(numberOfYears);
        premiumType.setAccrualToSalary(accrualToSalary);
        premiumType.setAccrualToVacation(accrualToVacation);

        return premiumTypesRepository.save(premiumType);
    }

    @RequestMapping(path = "/premiumType/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable(name = "id") long id) {
        premiumTypesRepository.deleteById(id);
    }

    @RequestMapping(path = "/premiumType/{id}", method = RequestMethod.PUT)
    public PremiumType update(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "numberOfYears") int numberOfYears,
            @RequestParam(name = "accrualToSalary") float accrualToSalary,
            @RequestParam(name = "accrualToVacation") float accrualToVacation) {

        PremiumType premiumType = premiumTypesRepository.findById(id).get();
        premiumType.setNumberOfYears(numberOfYears);
        premiumType.setAccrualToSalary(accrualToSalary);
        premiumType.setAccrualToVacation(accrualToVacation);

        return premiumTypesRepository.save(premiumType);
    }

    @RequestMapping(path = "/premiumType", method = RequestMethod.GET)
    public List<PremiumType> getAll() {
        return premiumTypesRepository.findAll();
    }

}

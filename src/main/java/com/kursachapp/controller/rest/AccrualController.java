package com.kursachapp.controller.rest;

import com.kursachapp.domain.entity.Accrual;
import com.kursachapp.domain.entity.AccrualType;
import com.kursachapp.domain.entity.Employee;
import com.kursachapp.repository.AccrualTypeRepository;
import com.kursachapp.repository.AccrualsRepository;
import com.kursachapp.repository.EmployeesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping(value="/api")
@Slf4j
public class AccrualController {

    @Autowired
    private AccrualsRepository accrualsRepository;
    @Autowired
    private AccrualTypeRepository accrualTypeRepository;
    @Autowired
    private EmployeesRepository employeesRepository;

    @RequestMapping(path = "/accrual", method = RequestMethod.POST)
    public Accrual save(
            @RequestParam(name = "employeeId") long employeeId,
            @RequestParam(name = "accrualTypeId") long accrualTypeId,
            @RequestParam(name = "days") int days) {

        Employee employee = employeesRepository.findById(employeeId).get();
        AccrualType accrualType = accrualTypeRepository.findById(accrualTypeId).get();

        Accrual accrual = new Accrual();
        accrual.setAccrualType(accrualType);
        accrual.setDate(new Date());
        accrual.setDays(days);
        accrual.setEmployee(employee);
        log.info("Accrual to save" + accrual);
        return accrualsRepository.save(accrual);
    }

    @RequestMapping(path = "/accrual/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable(name = "id") long id) {
        accrualsRepository.deleteById(id);
    }

    @RequestMapping(path = "/accrual/{id}", method = RequestMethod.PUT)
    public Accrual update(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "accrualTypeId") long accrualTypeId,
            @RequestParam(name = "days") int days,
            @RequestParam(name = "sum") float sum) {

        AccrualType accrualType = accrualTypeRepository.findById(accrualTypeId).get();

        Accrual accrual = accrualsRepository.findById(id).get();
        accrual.setAccrualType(accrualType);
        accrual.setDays(days);
        accrual.setSum(sum);
        log.info("Accrual to save" + accrual);
        return accrualsRepository.save(accrual);
    }

    @RequestMapping(path = "/accrual", method = RequestMethod.GET)
    public List<Accrual> getAll() {
        return accrualsRepository.findAll();
    }

}

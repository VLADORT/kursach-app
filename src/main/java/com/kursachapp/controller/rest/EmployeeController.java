package com.kursachapp.controller.rest;

import com.kursachapp.domain.entity.*;
import com.kursachapp.domain.view.EmployeesCurrentlyInVacationView;
import com.kursachapp.repository.EmployeesCurrentlyInVacationViewRepository;
import com.kursachapp.repository.EmployeesRepository;
import com.kursachapp.repository.PositionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private PositionsRepository positionsRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private EmployeesCurrentlyInVacationViewRepository employeesCurrentlyInVacationViewRepository;

    @RequestMapping(path = "/employee", method = RequestMethod.POST)
    public Employee save(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "middleName") String middleName,
            @RequestParam(name = "personnelNumber") String personnelNumber,
            @RequestParam(name = "positionId") long positionId,
            @RequestParam(name = "passportNumber") String passportNumber,
            @RequestParam(name = "identificationCode") int identificationCode,
            @RequestParam(name = "personalSalaryAllowance") int personalSalaryAllowance) {

        Position position = positionsRepository.findById(positionId).get();

        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setMiddleName(middleName);
        employee.setPersonnelNumber(personnelNumber);
        employee.setPosition(position);
        employee.setPassportNumber(passportNumber);
        employee.setIdentificationCode(identificationCode);
        employee.setPersonalSalaryAllowance(personalSalaryAllowance);
        employee.setStartDate(new Date());

        log.info("Employee to save" + employee);
        return employeesRepository.save(employee);
    }

    @RequestMapping(path = "/employee/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable(name = "id") long id) {
        employeesRepository.deleteById(id);
    }

    @RequestMapping(path = "/employee/{id}", method = RequestMethod.PUT)
    public Employee update(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "surname") String surname,
            @RequestParam(name = "middleName") String middleName,
            @RequestParam(name = "personnelNumber") String personnelNumber,
            @RequestParam(name = "positionId") long positionId,
            @RequestParam(name = "passportNumber") String passportNumber,
            @RequestParam(name = "identificationCode") int identificationCode,
            @RequestParam(name = "personalSalaryAllowance") int personalSalaryAllowance) {

        Position position = positionsRepository.findById(positionId).get();

        Employee employee = employeesRepository.findById(id).get();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setMiddleName(middleName);
        employee.setPersonnelNumber(personnelNumber);
        employee.setPosition(position);
        employee.setPassportNumber(passportNumber);
        employee.setIdentificationCode(identificationCode);
        employee.setPersonalSalaryAllowance(personalSalaryAllowance);

        return employeesRepository.save(employee);
    }

    @RequestMapping(path = "/employee", method = RequestMethod.GET)
    public List<Employee> getAll() {
        return employeesRepository.findAll();
    }

    @RequestMapping(path = "/employee/currentlyInVacation", method = RequestMethod.GET)
    public List<EmployeesCurrentlyInVacationView> getAllInVacation() {
        return employeesCurrentlyInVacationViewRepository.findAll();
    }

    @RequestMapping(path = "/employee/currentYearInVacation", method = RequestMethod.GET)
    public List<Employee> findAllThatTookVacationCurrentYear() {
        return employeesRepository.findAllThatTookVacationCurrentYear();
    }

    @RequestMapping(path = "/employee/findEmployeesWithLongestVacation", method = RequestMethod.GET)
    public List<TheLongestVacations> findEmployeesWithLongestVacation() {
        return employeesRepository.findEmployeesWithLongestVacation();
    }

    @RequestMapping(path = "/employee/findTheLongestWorkersByPosition", method = RequestMethod.GET)
    public List<TheLongestWorkersByPosition> findTheLongestWorkersByPosition() {
        return employeesRepository.findTheLongestWorkersByPosition();
    }

    @RequestMapping(path = "/employee/getAccrualsSumForEachYear", method = RequestMethod.GET)
    public List<AccrualsSumForEachYear> getAccrualsSumForEachYear(@RequestParam(name = "id") long id) {
        return employeesRepository.getAccrualsSumForEachYear(id);
    }

    @RequestMapping(path = "/employee/findAllByEveryMonthVacationForLastThreeMonths", method = RequestMethod.GET)
    public List<EveryMonthForLastThreeMonthsVacationEmployee> findAllByEveryMonthVacationForLastThreeMonths() {
        return employeesRepository.findAllByEveryMonthVacationForLastThreeMonths();
    }

    @RequestMapping(path = "/employee/findAllByMostFrequentVacation", method = RequestMethod.GET)
    public List<MostFrequentVacationEmployees> findAllByMostFrequentVacation() {
        return employeesRepository.findAllByMostFrequentVacation();
    }
}

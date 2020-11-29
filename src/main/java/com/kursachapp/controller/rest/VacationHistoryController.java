package com.kursachapp.controller.rest;

import com.kursachapp.domain.entity.Employee;
import com.kursachapp.domain.entity.VacationHistory;
import com.kursachapp.domain.view.LastYearVacationView;
import com.kursachapp.repository.EmployeesRepository;
import com.kursachapp.repository.LastYearVacationViewRepository;
import com.kursachapp.repository.VacationHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class VacationHistoryController {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yy-MM-dd");

    @Autowired
    private VacationHistoryRepository vacationHistoryRepository;
    @Autowired
    private EmployeesRepository employeesRepository;
    @Autowired
    private LastYearVacationViewRepository lastYearVacationViewRepository;

    @RequestMapping(path = "/vacationHistory", method = RequestMethod.POST)
    public VacationHistory save(
            @RequestParam(name = "employeeId") long employeeId,
            @RequestParam(name = "vacationStart") String vacationStart,
            @RequestParam(name = "vacationEnd") String vacationEnd) throws ParseException {

        Employee employee = employeesRepository.findById(employeeId).get();

        VacationHistory vacationHistory = new VacationHistory();

        vacationHistory.setEmployee(employee);
        vacationHistory.setVacationStart(FORMATTER.parse(vacationStart));
        vacationHistory.setVacationEnd(FORMATTER.parse(vacationEnd));

        return vacationHistoryRepository.save(vacationHistory);
    }

    @RequestMapping(path = "/vacationHistory/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable(name = "id") long id) {
        vacationHistoryRepository.deleteById(id);
    }

    @RequestMapping(path = "/vacationHistory/{id}", method = RequestMethod.PUT)
    public VacationHistory update(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "vacationStart") String vacationStart,
            @RequestParam(name = "vacationEnd") String vacationEnd) throws ParseException {

        VacationHistory vacationHistory = vacationHistoryRepository.findById(id).get();
        vacationHistory.setVacationStart(FORMATTER.parse(vacationStart));
        vacationHistory.setVacationEnd(FORMATTER.parse(vacationEnd));
        vacationHistory.setVacationLeft(null);
        return vacationHistoryRepository.save(vacationHistory);
    }

    @RequestMapping(path = "/vacationHistory", method = RequestMethod.GET)
    public List<VacationHistory> getAll() {
        return vacationHistoryRepository.findAll();
    }

    @RequestMapping(path = "/vacationHistory/getByEmployee/{id}", method = RequestMethod.GET)
    public List<VacationHistory> getAllByEmployee(@PathVariable(name = "id") long id) {
        return vacationHistoryRepository.findByEmployeeId(id);
    }

    @RequestMapping(path = "/vacationHistory/getByLastYear", method = RequestMethod.GET)
    public List<LastYearVacationView> getAllByLastYear() {
        return lastYearVacationViewRepository.findAll();
    }

}

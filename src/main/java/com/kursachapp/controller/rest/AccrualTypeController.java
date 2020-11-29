package com.kursachapp.controller.rest;

import com.kursachapp.domain.entity.AccrualType;
import com.kursachapp.repository.AccrualTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping(value="/api")
@Slf4j
public class AccrualTypeController {

    @Autowired
    private AccrualTypeRepository accrualTypeRepository;

    @RequestMapping(path = "/accrualType", method = RequestMethod.POST)
    public AccrualType save(@RequestParam(name = "name") String name) {
        AccrualType accrualType = new AccrualType();
        accrualType.setName(name);
        log.info("Accrual type to save" + accrualType);
        return accrualTypeRepository.save(accrualType);
    }

    @RequestMapping(path = "/accrualType/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable(name = "id") long id) {
        accrualTypeRepository.deleteById(id);
    }

    @RequestMapping(path = "/accrualType/{id}", method = RequestMethod.PUT)
    public AccrualType update(@PathVariable(name = "id") long id, @RequestParam(name = "name") String name) {
        AccrualType accrualType = accrualTypeRepository.findById(id).get();
        accrualType.setName(name);
        log.info("Accrual type to save" + accrualType);
        return accrualTypeRepository.save(accrualType);
    }

    @RequestMapping(path = "/accrualType", method = RequestMethod.GET)
    public List<AccrualType> getAll() {
        return accrualTypeRepository.findAll();
    }

}

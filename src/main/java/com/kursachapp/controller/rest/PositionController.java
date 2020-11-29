package com.kursachapp.controller.rest;

import com.kursachapp.domain.entity.Position;
import com.kursachapp.repository.PositionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class PositionController {

    @Autowired
    private PositionsRepository positionsRepository;

    @RequestMapping(path = "/position", method = RequestMethod.POST)
    public Position save(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "salary") float salary,
            @RequestParam(name = "standardVacation") int standardVacation) {

        Position position = new Position();

        position.setPositionName(name);
        position.setSalary(salary);
        position.setStandardVacation(standardVacation);

        return positionsRepository.save(position);
    }

    @RequestMapping(path = "/position/{id}", method = RequestMethod.DELETE)
    public void save(@PathVariable(name = "id") long id) {
        positionsRepository.deleteById(id);
    }

    @RequestMapping(path = "/position/{id}", method = RequestMethod.PUT)
    public Position update(
            @PathVariable(name = "id") long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "salary") float salary,
            @RequestParam(name = "standardVacation") int standardVacation) {

        Position position = positionsRepository.findById(id).get();

        position.setPositionName(name);
        position.setSalary(salary);
        position.setStandardVacation(standardVacation);

        return positionsRepository.save(position);
    }

    @RequestMapping(path = "/position", method = RequestMethod.GET)
    public List<Position> getAll() {
        return positionsRepository.findAll();
    }

}

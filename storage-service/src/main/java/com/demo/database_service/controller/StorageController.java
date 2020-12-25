package com.demo.database_service.controller;

import com.demo.database_service.domain.RecognitionResultEntity;
import com.demo.database_service.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class StorageController {

    @Autowired
    private StorageService storageService;

    @RequestMapping(path = "/recognition-result", method = RequestMethod.GET)
    public ResponseEntity<List<RecognitionResultEntity>> getAll() {
        return ResponseEntity.ok(storageService.getAll());
    }

    @RequestMapping(path = "/recognition-result/{resultId}", method = RequestMethod.GET)
    public ResponseEntity<RecognitionResultEntity> getOneById(
            @RequestParam(name = "resultId") long resultId) {
        return ResponseEntity.of(storageService.getOneById(resultId));
    }

    @RequestMapping(path = "/recognition-result/{resultId}", method = RequestMethod.DELETE)
    public void deleteOneById(
            @RequestParam(name = "resultId") long resultId) {
        storageService.deleteOneById(resultId);
    }

    @RequestMapping(path = "/recognition-result/{resultId}", method = RequestMethod.PUT)
    public void updateOneById(
            @RequestBody RecognitionResultEntity recognitionResult,
            @RequestParam(name = "resultId") long resultId) {
        storageService.updateOneById(resultId, recognitionResult);
    }

    @RequestMapping(path = "/recognition-result", method = RequestMethod.POST)
    public ResponseEntity<RecognitionResultEntity> create(
            @RequestBody RecognitionResultEntity recognitionResult) {
        return ResponseEntity.of(storageService.create(recognitionResult));
    }
}

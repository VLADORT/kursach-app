package com.demo.database_service.service.impl;

import com.demo.database_service.domain.RecognitionResultEntity;
import com.demo.database_service.repository.RecognitionResultRepository;
import com.demo.database_service.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private RecognitionResultRepository recognitionResultRepository;

    @Override
    public List<RecognitionResultEntity> getAll() {
        return recognitionResultRepository.findAll();
    }

    @Override
    public Optional<RecognitionResultEntity> getOneById(long resultId) {
        return recognitionResultRepository.findById(resultId);
    }

    @Override
    public void deleteOneById(long resultId) {
        recognitionResultRepository.deleteById(resultId);
    }

    @Override
    public void updateOneById(long resultId, RecognitionResultEntity recognitionResult) {
        RecognitionResultEntity oldEntity = getOneById(resultId).orElseThrow(IllegalArgumentException::new);
        recognitionResult.setId(oldEntity.getId());
        recognitionResultRepository.save(recognitionResult);
    }

    @Override
    public Optional<RecognitionResultEntity> create(RecognitionResultEntity recognitionResult) {
        return Optional.of(recognitionResultRepository.save(recognitionResult));
    }
}

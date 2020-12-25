package com.demo.database_service.service;

import com.demo.database_service.domain.RecognitionResultEntity;
import java.util.List;
import java.util.Optional;

public interface StorageService {

    List<RecognitionResultEntity> getAll();

    Optional<RecognitionResultEntity> getOneById(long resultId);

    void deleteOneById(long resultId);

    void updateOneById(long resultId, RecognitionResultEntity recognitionResult);

    Optional<RecognitionResultEntity> create(RecognitionResultEntity recognitionResult);
}

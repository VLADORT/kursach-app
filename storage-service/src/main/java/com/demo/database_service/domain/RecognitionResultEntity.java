package com.demo.database_service.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "recognition_result")
public class RecognitionResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private byte[] data;
    private int percentage;

}

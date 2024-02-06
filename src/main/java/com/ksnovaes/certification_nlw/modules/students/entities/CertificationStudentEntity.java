package com.ksnovaes.certification_nlw.modules.students.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificationStudentEntity {
    private UUID studentID;
    private UUID id;
    private String technology;
    private Integer grade;
    private List<AnswersCertificationEntity> answersCertificationEntities;
}

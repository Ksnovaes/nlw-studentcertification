package com.ksnovaes.certification_nlw.modules.students.useCases;

import com.ksnovaes.certification_nlw.modules.questions.entities.QuestionEntity;
import com.ksnovaes.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.ksnovaes.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.ksnovaes.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.ksnovaes.certification_nlw.modules.students.entities.AnswersCertificationEntity;
import com.ksnovaes.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.ksnovaes.certification_nlw.modules.students.entities.StudentEntity;
import com.ksnovaes.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.ksnovaes.certification_nlw.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUC {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    @Autowired
    private VerifyIfHasCertificationUC verifyIfHasCertificationUC;


    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {
        var hasCertification = this.verifyIfHasCertificationUC.execute(new VerifyHasCertificationDTO(
                dto.getEmail(),
                dto.getTechnology()
        ));

        if (hasCertification) {
            throw new Exception("You already have your certification!");
        }

        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationEntity> answersCertifications = new ArrayList<>();
        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionsAnswers().stream().forEach(questionAnswer -> {
            var question = questionsEntity.stream()
                    .filter(questionFilter -> questionFilter.getId()
                    .equals(questionAnswer.getQuestionID()))
                    .findFirst().get();

            var findCorrectAlternative = question.getAlternatives().stream()
                    .filter(alternative -> alternative.isCorrect())
                    .findFirst().get();
            if (findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answersCertificationEntity = AnswersCertificationEntity.builder()
                    .answerID(questionAnswer.getAlternativeID())
                    .questionID(questionAnswer.getQuestionID())
                    .isCorrect(questionAnswer.isCorrect())
                    .build();

            answersCertifications.add(answersCertificationEntity);
        });

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        } else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity =
                CertificationStudentEntity.builder()
                        .technology(dto.getTechnology())
                        .studentID(studentID)
                        .grade(correctAnswers.get())
                        .build();
        var certificationStudent = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationID(certificationStudentEntity.getId());
            answerCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationEntities(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudent;
    }
}

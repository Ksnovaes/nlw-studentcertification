package com.ksnovaes.certification_nlw.modules.students.controllers;

import com.ksnovaes.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.ksnovaes.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.ksnovaes.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.ksnovaes.certification_nlw.modules.students.useCases.StudentCertificationAnswersUC;
import com.ksnovaes.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private VerifyIfHasCertificationUC verifyIfHasCertificationUC;

    @Autowired
    private StudentCertificationAnswersUC studentCertificationAnswersUC;
    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        var result = this.verifyIfHasCertificationUC.execute(verifyHasCertificationDTO);
        if (result) {
            return "User already taken the test";
        }
        return "User can do the test";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer (@RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO){
        try {
            var result = studentCertificationAnswersUC.execute(studentCertificationAnswerDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

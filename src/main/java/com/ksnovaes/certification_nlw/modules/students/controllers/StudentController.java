package com.ksnovaes.certification_nlw.modules.students.controllers;

import com.ksnovaes.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.ksnovaes.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private VerifyIfHasCertificationUC verifyIfHasCertificationUC;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {
        var result = this.verifyIfHasCertificationUC.execute(verifyHasCertificationDTO);
        if (result) {
            return "User already taken the test";
        }
        return "User can do the test";
    }
}

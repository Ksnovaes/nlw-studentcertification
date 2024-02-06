package com.ksnovaes.certification_nlw.modules.students.useCases;

import com.ksnovaes.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import org.springframework.stereotype.Service;

@Service
public class VerifyIfHasCertificationUC {
    public boolean execute(VerifyHasCertificationDTO dto) {
        if (dto.getEmail().equals("kauanovais84@gmail.com") && dto.getTechnology().equals("JAVA")) {
            return true;
        }
        return false;
    }
}

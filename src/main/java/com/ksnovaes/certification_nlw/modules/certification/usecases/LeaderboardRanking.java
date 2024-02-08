package com.ksnovaes.certification_nlw.modules.certification.usecases;

import com.ksnovaes.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.ksnovaes.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardRanking {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    public List<CertificationStudentEntity> execute() {
        return this.certificationStudentRepository.findLeaderboardGradeDesc();
    }
}

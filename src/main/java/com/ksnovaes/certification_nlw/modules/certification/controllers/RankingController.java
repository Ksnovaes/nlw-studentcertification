package com.ksnovaes.certification_nlw.modules.certification.controllers;

import com.ksnovaes.certification_nlw.modules.certification.usecases.LeaderboardRanking;
import com.ksnovaes.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.ksnovaes.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private CertificationStudentRepository certificationStudentRepository;
    @Autowired
    private LeaderboardRanking leaderboardRanking;

    @GetMapping("/leaderboard")
    public List<CertificationStudentEntity> leaderboard() {
        var result = this.certificationStudentRepository.findLeaderboardGradeDesc();
        return result;
    }
}

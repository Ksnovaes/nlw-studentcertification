package com.ksnovaes.certification_nlw.modules.questions.controllers;

import com.ksnovaes.certification_nlw.modules.questions.dto.AlternativeResultDTO;
import com.ksnovaes.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.ksnovaes.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.ksnovaes.certification_nlw.modules.questions.entities.QuestionEntity;
import com.ksnovaes.certification_nlw.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = this.questionRepository.findByTechnology(technology);
        var toMap = result.stream().map(question -> mapQuestionToDTO(question))
                .collect(Collectors.toList());
        return toMap;
    }

    static QuestionResultDTO mapQuestionToDTO(QuestionEntity question) {
        var questionResultDTO = QuestionResultDTO.builder()
                .id(question.getId())
                .technology(question.getTechnology())
                .description(question.getDescription()).build();
        List<AlternativeResultDTO> alternativeResultDTOs = question.getAlternatives()
                .stream().map(alternative -> mapAlternativeDTO(alternative))
                .collect(Collectors.toList());
        questionResultDTO.setAlternatives(alternativeResultDTOs);
        return questionResultDTO;
    }

    static AlternativeResultDTO mapAlternativeDTO(AlternativesEntity alternatives) {
        return AlternativeResultDTO.builder()
                .id(alternatives.getId())
                .description(alternatives.getDescription()).build();
    }
}

package com.foro.hubApi.controller;

import com.foro.hubApi.domain.answers.AnswerService;
import com.foro.hubApi.domain.answers.dtos.AnswerRequestDTO;
import com.foro.hubApi.domain.answers.dtos.AnswerResponseDTO;
import com.foro.hubApi.domain.topics.dtos.TopicRequestDTO;
import com.foro.hubApi.domain.topics.dtos.TopicSimpleDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/answers")
@SecurityRequirement(name = "bearer-key")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponseDTO> createAnswer(@RequestBody @Valid AnswerRequestDTO datos) {

        var result =answerService.createAnswer(datos);

        return ResponseEntity.ok(result);
    }

}

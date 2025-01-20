package com.foro.hubApi.controller;

import com.foro.hubApi.domain.answers.AnswerService;
import com.foro.hubApi.domain.answers.dtos.AnswerRequestDTO;
import com.foro.hubApi.domain.answers.dtos.AnswerResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/answers")
@SecurityRequirement(name = "bearer-key")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponseDTO> createAnswer(@RequestBody @Valid AnswerRequestDTO datos) throws URISyntaxException {

        var answer =answerService.createAnswer(datos);
        URI url = new URI("/api/answers/" + answer.id());
        return ResponseEntity.created(url).body(answer);

    }

}

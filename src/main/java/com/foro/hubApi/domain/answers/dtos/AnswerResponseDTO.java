package com.foro.hubApi.domain.answers.dtos;

import com.foro.hubApi.domain.answers.Answer;
import com.foro.hubApi.domain.topics.Topic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AnswerResponseDTO(
        Long id,
        String user,
        String message,
        LocalDateTime createAt
) {
    public AnswerResponseDTO(Answer answer) {
        this(answer.getId(), answer.getUser().getUsername(), answer.getMessage(), answer.getCreatedAt());

    }


}

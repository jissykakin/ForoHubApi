package com.foro.hubApi.domain.answers.dtos;

import com.foro.hubApi.domain.topics.Topic;
import com.foro.hubApi.domain.users.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerRequestDTO(
        @NotNull(message = "El campo tópico es obligatorio")
        Long topicId,
        @NotNull(message = "El campo Usuario es obligatorio")
        String message
) {
}

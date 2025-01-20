package com.foro.hubApi.domain.topics.dtos;

import com.foro.hubApi.domain.topics.interfaces.TypeTopic;
import com.foro.hubApi.infra.validations.ValidEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TopicRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        @ValidEnum(enumClass = TypeTopic.class)
        TypeTopic type,
        @NotNull(message = "El campo Cursos es obligatorio")
        Long idCourses
) {
}

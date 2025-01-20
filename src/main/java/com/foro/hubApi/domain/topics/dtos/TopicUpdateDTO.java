package com.foro.hubApi.domain.topics.dtos;

import com.foro.hubApi.domain.topics.interfaces.TypeTopic;
import com.foro.hubApi.infra.validations.ValidEnum;

public record TopicUpdateDTO(
        String title,
        String message,
        @ValidEnum(enumClass = TypeTopic.class)
        TypeTopic type
) {
}

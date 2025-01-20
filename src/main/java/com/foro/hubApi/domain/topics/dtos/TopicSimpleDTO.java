package com.foro.hubApi.domain.topics.dtos;

import com.foro.hubApi.domain.topics.Topic;
import com.foro.hubApi.domain.topics.interfaces.TypeTopic;

import java.time.LocalDateTime;

public record TopicSimpleDTO(
        String title,
        TypeTopic type,
        LocalDateTime createdAt,
        String user,
        String course,
        Long countAnswers
) {
    public TopicSimpleDTO(Topic topic) {
        this(topic.getTitle(),topic.getType(),topic.getCreatedAt(),topic.getUser().getUsername(), topic.getCourse().getName(), (long) topic.getAnswers().size() );
    }
}

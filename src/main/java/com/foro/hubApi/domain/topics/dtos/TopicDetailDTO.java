package com.foro.hubApi.domain.topics.dtos;

import com.foro.hubApi.domain.answers.Answer;
import com.foro.hubApi.domain.answers.dtos.AnswerResponseDTO;
import com.foro.hubApi.domain.topics.Topic;
import com.foro.hubApi.domain.topics.interfaces.TypeTopic;

import java.time.LocalDateTime;
import java.util.List;

public record TopicDetailDTO(
        Long id,
        String title,
        String message,
        TypeTopic type,
        LocalDateTime createdAt,
        String user,
        String course,
        List<AnswerResponseDTO> answers
) {

    public TopicDetailDTO(Topic topic) {
        this(topic.getId(),topic.getTitle(),topic.getMessage(),topic.getType(),topic.getCreatedAt(),topic.getUser().getUsername(), topic.getCourse().getName(), topic.getAnswers().stream().map(AnswerResponseDTO::new).toList());
    }
}

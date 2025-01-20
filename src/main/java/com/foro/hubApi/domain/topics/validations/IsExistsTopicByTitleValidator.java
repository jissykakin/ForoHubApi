package com.foro.hubApi.domain.topics.validations;

import com.foro.hubApi.domain.topics.TopicRepository;
import com.foro.hubApi.domain.topics.dtos.TopicRequestDTO;
import com.foro.hubApi.domain.topics.dtos.TopicUpdateDTO;
import com.foro.hubApi.exception.ExceptionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsExistsTopicByTitleValidator implements ValidatorsTopics {

    @Autowired
    TopicRepository topicRepository;

    @Override
    public void validatorTopic(Object datos, Long id) {
        if (datos instanceof TopicRequestDTO requestDTO) {
            if (topicRepository.existsByTitleAndStatusTrue(requestDTO.title())) {
                throw new ExceptionValidation("Ya existe un tópico con ese título");
            }
        } else if (datos instanceof TopicUpdateDTO updateDTO) {
            if (topicRepository.existsByTitleAndStatusTrue(updateDTO.title())
                    && !topicRepository.findById(id).get().getTitle().equals(updateDTO.title())) {
                throw new ExceptionValidation("Ya existe un tópico con ese título");
            }
        }

    }
}

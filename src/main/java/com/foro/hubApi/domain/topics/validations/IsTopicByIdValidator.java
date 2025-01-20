package com.foro.hubApi.domain.topics.validations;

import com.foro.hubApi.domain.topics.TopicRepository;
import com.foro.hubApi.domain.topics.dtos.TopicRequestDTO;
import com.foro.hubApi.domain.topics.dtos.TopicUpdateDTO;
import com.foro.hubApi.exception.ExceptionValidation;
import com.foro.hubApi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsTopicByIdValidator {

    @Autowired
    TopicRepository topicRepository;

    public void validatorTopic(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        var topic = topicRepository.existsByIdAndStatusTrue(id);
        if (!topic) {
            throw new ResourceNotFoundException("El Id del Topic no existe o esta desactivado");
        }
    }

}

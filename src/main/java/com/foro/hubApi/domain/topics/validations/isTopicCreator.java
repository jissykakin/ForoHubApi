package com.foro.hubApi.domain.topics.validations;

import com.foro.hubApi.domain.topics.TopicRepository;
import com.foro.hubApi.domain.topics.dtos.TopicUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class isTopicCreator implements ValidatorsTopics {
    @Autowired
    TopicRepository topicRepository;

    @Override
    public void validatorTopic(Object datos, Long id) {

        if (datos instanceof TopicUpdateDTO) {

            var topic = topicRepository.getReferenceById(id);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            var isTopicCreator = topic.getUser().getUsername().equals(userDetails.getUsername());

            if (!isTopicCreator) {
                throw new AccessDeniedException("No tienes permisos para eliminar este tema");
            }
        }

    }
}

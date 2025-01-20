package com.foro.hubApi.domain.answers;

import com.foro.hubApi.domain.answers.dtos.AnswerRequestDTO;
import com.foro.hubApi.domain.answers.dtos.AnswerResponseDTO;
import com.foro.hubApi.domain.topics.Topic;
import com.foro.hubApi.domain.topics.TopicRepository;
import com.foro.hubApi.domain.topics.dtos.TopicDetailDTO;
import com.foro.hubApi.domain.topics.dtos.TopicRequestDTO;
import com.foro.hubApi.domain.topics.dtos.TopicSimpleDTO;
import com.foro.hubApi.domain.users.User;
import com.foro.hubApi.domain.users.UserRepository;
import com.foro.hubApi.exception.ExceptionValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public AnswerResponseDTO createAnswer(@Valid AnswerRequestDTO answerDTO) {

//        validatorsTopicsList.forEach(v -> v.validatorTopic(topicDto, null));

        //consulta topic
        var topic = topicRepository.findById(answerDTO.topicId())
                .orElseThrow(() -> new ExceptionValidation("El Id del Tópico no existe"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = (User)  authentication.getPrincipal();

        //consulta de Usuario
        var user = userRepository.findByUsername(userAuth.getUsername());

        //TODO:validar si esta desactivado

        var answer = new Answer(topic, user, answerDTO.message());
        answerRepository.save(answer);
        return new AnswerResponseDTO(answer);
    }
}

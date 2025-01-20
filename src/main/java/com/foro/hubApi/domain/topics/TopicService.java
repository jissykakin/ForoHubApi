package com.foro.hubApi.domain.topics;

import com.foro.hubApi.domain.answers.dtos.AnswerResponseDTO;
import com.foro.hubApi.domain.courses.CoursesRepository;
import com.foro.hubApi.domain.topics.dtos.*;
import com.foro.hubApi.domain.topics.validations.IsTopicByIdValidator;
import com.foro.hubApi.domain.topics.validations.ValidatorsTopics;
import com.foro.hubApi.domain.users.User;
import com.foro.hubApi.domain.users.UserRepository;
import com.foro.hubApi.exception.ExceptionValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private List<ValidatorsTopics> validatorsTopicsList;

    @Autowired
    private IsTopicByIdValidator isTopicByIdValidator;



    public TopicDetailDTO getTopic(Long id){

        isTopicByIdValidator.validatorTopic(id);

        Topic topicSearch = topicRepository.fetchByIdWithAnswers(id);

        return  new TopicDetailDTO(topicSearch.getId(), topicSearch.getTitle(),topicSearch.getMessage(),
                topicSearch.getType(), topicSearch.getCreatedAt(), topicSearch.getUser().getUsername(),
                topicSearch.getCourse().getName(), topicSearch.getAnswers().stream().map(AnswerResponseDTO::new).toList());

    }


    public TopicResponseDTO createTopic(@Valid TopicRequestDTO topicDto) {

        validatorsTopicsList.forEach(v -> v.validatorTopic(topicDto, null));

        //consulta de cursos
        var course = coursesRepository.findById(topicDto.idCourses())
                .orElseThrow(() -> new ExceptionValidation("El Id del Curso no existe"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = (User)  authentication.getPrincipal();

        //consulta de Usuario
        var user = userRepository.findByUsername(userAuth.getUsername());

//        //consulta de Usuario
//        var user = userRepository.findById(topicDto.idUser()).
//                orElseThrow(() -> new ExceptionValidation("El Id del Usuario no existe"));

        var topic = new Topic(topicDto.title(), topicDto.message(), topicDto.type(), user, course);
        topicRepository.save(topic);
        return new TopicResponseDTO(topic);
    }

    @Transactional
    public void updateTopic (@Valid TopicUpdateDTO topicDTO, Long id){

        isTopicByIdValidator.validatorTopic(id);

        validatorsTopicsList.forEach(v -> v.validatorTopic(topicDTO, id));

        var topic = topicRepository.getReferenceById(id);
        topic.updateTopic(topicDTO);

    }


    @Transactional
    public void deleteTopic (Long id){

        isTopicByIdValidator.validatorTopic(id);

        var topic = topicRepository.getReferenceById(id);
        topic.disableTopic();

    }
}

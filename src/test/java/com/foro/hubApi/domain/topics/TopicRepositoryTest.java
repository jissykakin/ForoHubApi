package com.foro.hubApi.domain.topics;

import com.foro.hubApi.domain.answers.Answer;
import com.foro.hubApi.domain.answers.AnswerRepository;
import com.foro.hubApi.domain.answers.dtos.AnswerResponseDTO;
import com.foro.hubApi.domain.courses.Course;
import com.foro.hubApi.domain.roles.RoleName;
import com.foro.hubApi.domain.topics.dtos.TopicDetailDTO;
import com.foro.hubApi.domain.topics.interfaces.TypeTopic;
import com.foro.hubApi.domain.users.User;
import com.foro.hubApi.domain.users.dtos.UserRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TopicRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Test
    @DisplayName("Test in findByCourseAndCreatedAt: Cuando el curso y el año de creacion son null deberia retornar todos los topicos activos")
    void findByCourseAndCreatedAt_whenCourseAndYearIsNull() {
        //given o arrange
        generateDataDefault();

        //when o act
        var topics = topicRepository.findByCourseAndCreatedAt(null, null, PageRequest.of(0, 10));

        //then o assert
        assertThat(topics.getTotalElements()).isEqualTo(5);
    }

    @Test
    @DisplayName("Test in findByCourseAndCreatedAt: Cuando solo el curso esta presente deberia retornar los topicos relacionados a el curso indicado solo activos")
    void findByCourseAndCreatedAt_whenYearIsNull() {
        //given o arrange

        String courseName = "Spring Boot";
        generateDataDefault();

        //when o act
        var topics = topicRepository.findByCourseAndCreatedAt(courseName, null, PageRequest.of(0, 10));

        //then o assert
        assertThat(topics.getTotalElements()).isEqualTo(3);
        topics.forEach(topic -> assertThat(topic.getCourse().getName()).containsIgnoringCase(courseName));
    }

    @Test
    @DisplayName("Test in findByCourseAndCreatedAt: Cuando solo el año esta presente deberia retornar topicos con ese año")
    public void findByCourseAndCreatedAt_whenOnlyYearIsPresent() {
        // Given
        generateDataDefault();
        int createdAt = LocalDateTime.now().getYear();
        String year = Integer.toString(createdAt);


        // When
        Page<Topic> topics = topicRepository.findByCourseAndCreatedAt(null, year, PageRequest.of(0, 10));

        // Then
        assertThat(topics).isNotEmpty();
        topics.forEach(topic -> assertThat(Year.of(topic.getCreatedAt().getYear())).isEqualTo(Year.of(Integer.parseInt(year))));
    }


    @Test
    @DisplayName("Test in findByCourseAndCreatedAt: Cuando no hay resultados")
    public void findByCourseAndCreatedAt_whenNoResults() {
        // Given
        generateDataDefault();
        String courseName = "NonExistentCourse";
        String year = "2022"; // Año en el que no hay temas para el curso especificado

        // When
        Page<Topic> topics = topicRepository.findByCourseAndCreatedAt(courseName, year, PageRequest.of(0, 10));

        // Then
        assertThat(topics).isEmpty();
    }

    @Test
    @DisplayName("Test in findByCourseAndCreatedAt: Cuando el curso y el año estan presentes, deberia retornar topicos que sean igual al curso y al año")
    public void findByCourseAndCreatedAt_whenBothCourseAndYearArePresent_shouldReturnTopicsForThatCourseAndYear() {
        // Given
        generateDataDefault();
        int createdAt = LocalDateTime.now().getYear();
        String year = Integer.toString(createdAt);
        String courseName = "Spring Boot";


        // When
        Page<Topic> topics = topicRepository.findByCourseAndCreatedAt(courseName, year, PageRequest.of(0, 10));

        // Then
        assertThat(topics).isNotEmpty();
        topics.forEach(topic -> {
            assertThat(topic.getCourse().getName()).containsIgnoringCase(courseName);
            assertThat(Year.of(topic.getCreatedAt().getYear())).isEqualTo(Year.of(Integer.parseInt(year)));
        });
    }

//Test segunda query


    @Test
    @DisplayName("Test in fetchByIdWithAnswers: Cuando retorna un topico con respuesta")
    @EntityGraph

    public void fetchByIdWithAnswers_shouldReturnTopicWithAnswers() {
        generateDataDefault();
        Topic expectedTopic = topicRepository.findByTitle("Topic1").orElse(null);
        Long topicId = expectedTopic.getId();
        System.out.println(expectedTopic.getId());
        // When
        Topic actualTopic = topicRepository.fetchByIdWithAnswers(topicId);
        System.out.println(actualTopic.getAnswers());
        // Then
        assertThat(actualTopic).isNotNull();
        assertThat(actualTopic.getId()).isEqualTo(topicId);
//        assertThat(actualTopic.getAnswers()).isNotEmpty();

    }

    @Test
    @DisplayName("Test in fetchByIdWithAnswers: Cuando  no retorna un topico")
    public void fetchByIdWithAnswers_shouldReturnNullWhenTopicNotFound() {
        // Given
        Long nonExistentId = 99999L;

        // When
        Topic topic = topicRepository.fetchByIdWithAnswers(nonExistentId);

        // Then
        assertThat(topic).isNull();
    }

    private Topic registerTopic(String title, String message, TypeTopic type, User user, Course course) {



        var topic = new Topic(title, message, type, user, course);
        em.persist(topic);
        return topic;
    }

    private Course registerCourse(String name) {
        var course = new Course(name);
        em.persist(course);
        return course;
    }


    private User registerUser(String username,
                              String password,
                              String email,
                              String avatar,
                              String profession,
                              String role) {

        var user = new User(new UserRequestDTO(username, password, email, role, avatar, profession));
        em.persist(user);
        return user;
    }

    private Answer registerAnswer(Topic topic, User user, String message) {

        var answer = new Answer(topic, user, message);
        em.persist(answer);
        return answer;
    }


    private void generateDataDefault() {
        var course1 = registerCourse("curso 1");
        var user1 = registerUser("user 1", "123456", "test@test.com", null, "profesion 1", "ROLE_USER");
        var topic1 = registerTopic("Topic1", "pruebas Topic 1", TypeTopic.BUG, user1, course1);
        var topic2 = registerTopic("Topic2", "pruebas Topic 2", TypeTopic.BUG, user1, course1);


        var course2 = registerCourse("Spring Boot");
        var user2 = registerUser("user 2", "123456", "test1@test.com", null, "profesion 1", "ROLE_USER");
        var topic3 = registerTopic("Topic3", "pruebas Topic 3", TypeTopic.BUG, user2, course2);
        var topic4 = registerTopic("Topic4", "pruebas Topic 4", TypeTopic.BUG, user2, course2);
        var topic5 = registerTopic("Topic5", "pruebas Topic 5", TypeTopic.BUG, user2, course2);

        var answer1 = registerAnswer(topic1, user2, "Prueba de Respuesta 1");
        var answer3 = registerAnswer(topic3, user1, "Prueba de Respuesta 2");




    }
}

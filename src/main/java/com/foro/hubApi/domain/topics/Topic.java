package com.foro.hubApi.domain.topics;

import com.foro.hubApi.domain.answers.Answer;
import com.foro.hubApi.domain.courses.Course;
import com.foro.hubApi.domain.topics.dtos.TopicRequestDTO;
import com.foro.hubApi.domain.topics.dtos.TopicUpdateDTO;
import com.foro.hubApi.domain.topics.interfaces.TypeTopic;
import com.foro.hubApi.domain.users.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topics")
@Entity(name = "Topic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean status = true;

    @Enumerated(EnumType.STRING)
    private TypeTopic type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic")
    private List<Answer> answers;

    public Topic(String title, String message, TypeTopic type, User user, Course course) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.user = user;
        this.course = course;
    }

    public void updateTopic(@Valid TopicUpdateDTO topicDTO) {
        if (topicDTO.message() != null) {
            this.message = topicDTO.message();
        }
        if (topicDTO.title() != null) {
            this.title = topicDTO.title();
        }
        if (topicDTO.type() != null) {
            this.type = topicDTO.type();
        }

    }

    public void disableTopic() {
        this.status = false;
    }



}

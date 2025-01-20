package com.foro.hubApi.controller;

import com.foro.hubApi.domain.courses.Course;
import com.foro.hubApi.domain.topics.Topic;
import com.foro.hubApi.domain.topics.TopicRepository;
import com.foro.hubApi.domain.topics.TopicService;
import com.foro.hubApi.domain.topics.dtos.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicsController {

    @Autowired
    TopicService topicService;

    @Autowired
    TopicRepository topicRepository;

    @GetMapping
    public Page<TopicSimpleDTO> getTopics(
            @RequestParam(value = "course", required = false) String course,
            @RequestParam(value = "year", required = false) String year,
            @PageableDefault(size = 10, sort = {"createdAt"}, direction = Sort.Direction.ASC) Pageable pageable) {


            return topicRepository.findByCourseAndCreatedAt(course, year, pageable).map(TopicSimpleDTO::new);

    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailDTO> getTopic(@PathVariable Long id) {
        var topic = topicService.getTopic(id);
        return ResponseEntity.ok(topic);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseDTO> createTopic(@RequestBody @Valid TopicRequestDTO datos) {

        var result =topicService.createTopic(datos);

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTopic(@RequestBody @Valid TopicUpdateDTO datos, @PathVariable Long id) {
       topicService.updateTopic(datos, id);
    }
//
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}

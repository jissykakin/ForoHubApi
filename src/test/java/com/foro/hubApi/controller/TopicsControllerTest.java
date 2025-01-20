package com.foro.hubApi.controller;

import com.foro.hubApi.domain.courses.Course;
import com.foro.hubApi.domain.topics.Topic;
import com.foro.hubApi.domain.topics.TopicRepository;
import com.foro.hubApi.domain.topics.TopicService;
import com.foro.hubApi.domain.topics.dtos.TopicRequestDTO;
import com.foro.hubApi.domain.topics.dtos.TopicResponseDTO;
import com.foro.hubApi.domain.topics.dtos.TopicSimpleDTO;
import com.foro.hubApi.domain.topics.dtos.TopicUpdateDTO;
import com.foro.hubApi.domain.topics.interfaces.TypeTopic;
import com.foro.hubApi.domain.users.User;
import com.foro.hubApi.exception.ExceptionValidation;
import com.foro.hubApi.exception.ResourceNotFoundException;
import com.foro.hubApi.infra.security.TokenService;
import jakarta.xml.bind.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TopicSimpleDTO> topicSimpleDTOJacksonTester ;

    @Autowired
    private JacksonTester<TopicResponseDTO> topicResponseDTOJacksonTester ;


    @Autowired
    private JacksonTester<TopicRequestDTO> topicRequestDTOJacksonTester ;

    @Autowired
    private JacksonTester<TopicUpdateDTO> topicUpdateDTOJacksonTester;

    @Autowired
    private TopicRepository topicRepository;

    @MockBean
    private TopicRepository topicRepositoryMock;

    @MockBean
    private TopicService topicServiceMock;



    @Test
    @DisplayName("Deberia devolver http 400 cuando la request no tenga datos")
    @WithMockUser
    void createTopic_shouldReturn400_whenRequestIsNotPresent() throws Exception {
        var response = mvc.perform(post("/api/topics"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deberia devolver http 200 cuando la request reciba un json valido")
    @WithMockUser
    void createTopic_shouldReturn200_whenRequestIsPresent() throws Exception {

        //Given
        var datosrequest = new TopicRequestDTO("Topic 1", "Mensaje topico 1", TypeTopic.DUDA,  2L);

        var datosresposeDetalle = new TopicResponseDTO("Topic 1", TypeTopic.DUDA, LocalDateTime.now(), "user", "curso");

        // when
        when(topicServiceMock.createTopic(any())).thenReturn(datosresposeDetalle);

        var response = mvc.perform(post("/api/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicRequestDTOJacksonTester.write(
                                datosrequest
                         ).getJson()
                        )
                )
                .andReturn().getResponse();

        var jsonEsperado = topicResponseDTOJacksonTester.write(
                datosresposeDetalle
        ).getJson();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }


    @Test
    @DisplayName("Debería devolver HttpStatus.NO_CONTENT cuando se actualiza un tema exitosamente")
    @WithMockUser
    void updateTopic_shouldReturn204_whenUpdateIsSuccessfull() throws Exception {
        // Given
        Long topicId = 1L;
        TopicUpdateDTO updateData = new TopicUpdateDTO("Updated Topic Title", "Updated message", TypeTopic.BUG);

        // Mock topicService.updateTopic to not return a value (void method)
        doNothing().when(topicServiceMock).updateTopic(any(TopicUpdateDTO.class), eq(topicId));

        // When
        ResultActions response = mvc.perform(put("/api/topics/{id}", topicId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(topicUpdateDTOJacksonTester.write(updateData).getJson())
        );

        // Then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería devolver HttpStatus.BAD_REQUEST cuando la request no tenga datos")
    @WithMockUser
    void updateTopic_shouldReturn400_whenRequestHasNoBody() throws Exception {
        // Given
        Long topicId = 1L;

        // When
        ResultActions response = mvc.perform(put("/api/topics/{id}", topicId));

        // Then
        response.andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Debería devolver HttpStatus.NO_CONTENT cuando se elimina un tema exitosamente")
    @WithMockUser
    void delete_shouldReturn204_whenDeleteIsSuccessfull() throws Exception {
        // Given
        Long topicId = 1L;

        doNothing().when(topicServiceMock).deleteTopic(eq(topicId));

        // When
        ResultActions response = mvc.perform(delete("/api/topics/{id}", topicId));

        // Then
        response.andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Debería devolver HttpStatus.NOT_FOUND cuando el tema no existe")
    @WithMockUser
    void delete_shouldReturn404_whenTopicNotFound() throws Exception {
        // Given
        Long topicId = 1L;

        // Mock topicService.deleteTopic to throw a ResourceNotFoundException
       doThrow(new ResourceNotFoundException("Topic not found")).when(topicServiceMock).deleteTopic(eq(topicId));

        // When
        ResultActions response = mvc.perform(delete("/api/topics/{id}", topicId));

        // Then
        response.andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Debería devolver HttpStatus.BAD_REQUEST cuando el ID es inválido")
    @WithMockUser
    void eliminar_shouldReturn400_whenIdIsInvalid() throws Exception {
        // Given
        String invalidId = "abc";

        // When
        ResultActions response = mvc.perform(delete("/api/topics/{id}", invalidId));

        // Then
        response.andExpect(status().isBadRequest());
    }

}
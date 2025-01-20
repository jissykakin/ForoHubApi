package com.foro.hubApi.domain.topics.validations;

import jakarta.validation.Valid;

public interface ValidatorsTopics {
       void validatorTopic(@Valid Object datos, Long id);
}

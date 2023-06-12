package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ActivityAvailableResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListActivityAvailableMapperTest {

    @Test
    @DisplayName("Deve mapear uma Atividade para ActivityAvailableResponse")
    void deveMapearAtividadeParaActivityAvailableResponse() {

        Activity activity = ActivityFactory.get();

        ActivityAvailableResponse response = ListActivityAvailableMapper.toResponse(activity);

        assertEquals(response.getId(), activity.getId());
        assertEquals(response.getTitle(), activity.getTitle());
        assertEquals(response.getLesson(), activity.getLesson());
        assertEquals(response.getCourseName(), activity.getCourse().getName());
    }
}

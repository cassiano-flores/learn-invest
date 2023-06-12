package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ActivityResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityMapperTest {

    @Test
    @DisplayName("Deve mapear uma Atividade para ActivityResponse")
    void deveMapearAtividadeParaActivityResponse() {

        Activity activity = ActivityFactory.get();

        ActivityResponse response = ActivityMapper.toResponse(activity);

        assertEquals(response.getId(), activity.getId());
        assertEquals(response.getTitle(), activity.getTitle());
    }
}

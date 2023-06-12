package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListCourseActivitiesResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCourseActivitiesMapperTest {

    @Test
    @DisplayName("Deve mapear uma Activity para ListCourseActivitiesResponse")
    void deveMapearUmaActivityParaListCourseActivitiesResponse() {

        Activity activity = ActivityFactory.get();

        ListCourseActivitiesResponse response = ListCourseActivitiesMapper.toResponse(activity);

        assertEquals(response.getId(), activity.getId());
        assertEquals(response.getTitle(), activity.getTitle());
    }
}

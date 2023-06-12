package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.factories.CourseFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListCourseMapperTest {

    @Test
    @DisplayName("Deve mapear um Curso para ListCourseResponse")
    void deveMapearCursoParaListCourseResponse() {

        Course course = CourseFactory.get();

        CourseResponse response = ListCourseMapper.toResponse(course);

        assertEquals(response.getId(), course.getId());
        assertEquals(response.getName(), course.getName());
        assertEquals(response.getNextCourseId(), course.getNextCourseId());
        assertEquals(response.getAchievementCompleteCourseId(), course.getAchievementCompleteCourseId());
    }
}

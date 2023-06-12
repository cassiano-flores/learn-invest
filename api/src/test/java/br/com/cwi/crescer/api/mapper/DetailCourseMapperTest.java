package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.factories.CourseFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DetailCourseMapperTest {

    @Test
    @DisplayName("Deve mapear um Curso para DetailCourseResponse")
    void deveMapearCursoParaDetailCourseResponse() {

        Course course = CourseFactory.get();

        CourseResponse response = DetailCourseMapper.toResponse(course);

        assertEquals(response.getId(), course.getId());
        assertEquals(response.getName(), course.getName());
        assertEquals(response.getNextCourseId(), course.getNextCourseId());
        assertEquals(response.getAchievementCompleteCourseId(), course.getAchievementCompleteCourseId());
        assertEquals(response.getActivities().size(), course.getActivities().size());

        for (int i = 0; i < response.getActivities().size(); i++) {
            assertEquals(response.getActivities().get(i).getId(), course.getActivities().get(i).getId());
        }
    }
}

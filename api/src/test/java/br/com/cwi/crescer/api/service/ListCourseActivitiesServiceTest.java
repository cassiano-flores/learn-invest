package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListCourseActivitiesResponse;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.factories.CourseFactory;
import br.com.cwi.crescer.api.mapper.ListCourseActivitiesMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ListCourseActivitiesServiceTest {

    @InjectMocks
    private ListCourseActivitiesService tested;

    @Mock
    private SearchCourseService searchCourseService;

    @Test
    @DisplayName("Deve listar as atividades do curso com sucesso")
    void deveListarAtividadesDoCursoComSucesso() {

        Course course = CourseFactory.get();
        Long courseId = course.getId();

        when(searchCourseService.byId(courseId)).thenReturn(course);

        List<ListCourseActivitiesResponse> activities = tested.list(courseId);

        verify(searchCourseService).byId(courseId);

        List<ListCourseActivitiesResponse> expectedActivities = course.getActivities().stream()
                .map(ListCourseActivitiesMapper::toResponse)
                .collect(toList());

        for (ListCourseActivitiesResponse activity : activities) {
            boolean questionExists = expectedActivities.stream()
                    .anyMatch(q -> q.getId().equals(activity.getId()));
            assertTrue(questionExists);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando curso não for encontrado")
    void deveLancarExcecaoQuandoCursoNaoForEncontrado() {

        Course course = CourseFactory.get();
        Long courseId = course.getId();

        when(searchCourseService.byId(courseId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Course not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.list(courseId));

        verify(searchCourseService).byId(courseId);
    }
}

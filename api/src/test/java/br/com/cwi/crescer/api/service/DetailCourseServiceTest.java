package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.factories.CourseFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class DetailCourseServiceTest {

    @InjectMocks
    private DetailCourseService tested;

    @Mock
    private SearchCourseService searchCourseService;

    @Test
    @DisplayName("Deve detalhar um curso com sucesso")
    void deveDetalharUmCursoComSucesso() {

        Course course = CourseFactory.get();
        Long courseId = course.getId();

        when(searchCourseService.byId(courseId)).thenReturn(course);

        CourseResponse response = tested.detail(courseId);

        verify(searchCourseService).byId(courseId);

        assertEquals(response.getId(), course.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando atividade não for encontrada")
    void deveLancarExcecaoQuandoAtividadeNaoForEncontrada() {

        Course course = CourseFactory.get();
        Long courseId = course.getId();

        when(searchCourseService.byId(courseId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Course not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.detail(courseId));

        verify(searchCourseService).byId(courseId);
    }
}

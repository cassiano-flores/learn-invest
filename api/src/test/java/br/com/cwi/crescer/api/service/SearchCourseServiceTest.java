package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.factories.CourseFactory;
import br.com.cwi.crescer.api.repository.CourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class SearchCourseServiceTest {

    @InjectMocks
    private SearchCourseService tested;

    @Mock
    private CourseRepository courseRepository;

    @Test
    @DisplayName("Deve retornar curso com sucesso")
    void deveRetornarCursoComSucesso() {

        Course course = CourseFactory.get();
        Long courseId = course.getId();

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course courseExpected = tested.byId(courseId);

        verify(courseRepository).findById(courseId);

        assertEquals(courseExpected, course);
    }

    @Test
    @DisplayName("Deve lançar exceção quando curso não for encontrado")
    void deveLancarExcecaoQuandoCursoNaoForEncontrado() {

        Course course = CourseFactory.get();
        Long courseId = course.getId();

        when(courseRepository.findById(courseId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Course not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.byId(courseId));

        verify(courseRepository).findById(courseId);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.factories.CourseFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.ListCourseMapper;
import br.com.cwi.crescer.api.repository.CourseRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ListCourseServiceTest {

    @InjectMocks
    private ListCourseService tested;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Test
    @DisplayName("Deve listar todos cursos com sucesso")
    void deveListarTodosCursosComSucesso() {

        Course course1 = CourseFactory.get();
        Course course2 = CourseFactory.get();
        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findAllByOrderById()).thenReturn(courses);

        List<CourseResponse> responses = tested.list();

        verify(courseRepository).findAllByOrderById();

        List<CourseResponse> expectedResponses = courses.stream()
                .map(ListCourseMapper::toResponse)
                .collect(Collectors.toList());

        assertEquals(expectedResponses.size(), responses.size());

        for (int i = 0; i < expectedResponses.size(); i++) {
            assertEquals(expectedResponses.get(i).getId(), responses.get(i).getId());
        }
    }

    @Test
    @DisplayName("Deve listar todos cursos disponíveis com sucesso")
    void deveListarTodosCursosDisponiveisComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(searchUsuarioService.byId(usuarioId)).thenReturn(usuario);

        List<CourseResponse> courses = tested.listAvailable();

        verify(searchUsuarioService).byId(usuarioId);

        List<CourseResponse> coursesAvailable = usuario.getCourses().stream()
                .map(ListCourseMapper::toResponse)
                .collect(Collectors.toList());

        for (CourseResponse course : courses) {
            boolean questionExists = coursesAvailable.stream()
                    .anyMatch(q -> q.getId().equals(course.getId()));
            assertTrue(questionExists);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(searchUsuarioService.byId(usuarioId)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.listAvailable());

        verify(searchUsuarioService).byId(usuarioId);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.mapper.ListCourseMapper;
import br.com.cwi.crescer.api.repository.CourseRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ListCourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    public List<CourseResponse> list() {

        return courseRepository.findAllByOrderById().stream()
                .map(ListCourseMapper::toResponse)
                .collect(toList());
    }

    public List<CourseResponse> listAvailable() {
        Usuario usuario = searchUsuarioService.byId(usuarioAutenticadoService.getId());

        return usuario.getCourses().stream()
                .map(ListCourseMapper::toResponse)
                .collect(toList());
    }
}

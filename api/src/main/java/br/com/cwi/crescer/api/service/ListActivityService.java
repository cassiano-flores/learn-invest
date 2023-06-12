package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ActivityResponse;
import br.com.cwi.crescer.api.mapper.ListActivityMapper;
import br.com.cwi.crescer.api.repository.ActivityRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ListActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    public List<ActivityResponse> list() {

        return activityRepository.findAllByOrderById().stream()
                .map(ListActivityMapper::toResponse)
                .collect(toList());
    }

    public List<ActivityResponse> listAvailable() {
        Usuario usuario = searchUsuarioService.byId(usuarioAutenticadoService.getId());

        return usuario.getActivities().stream()
                .map(ListActivityMapper::toResponse)
                .collect(toList());
    }
}

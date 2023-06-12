package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.UserAchievementResponse;
import br.com.cwi.crescer.api.mapper.UserAchievementMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAchievementService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public List<UserAchievementResponse> get() {
        Usuario usuario = usuarioAutenticadoService.get();

        return usuario.getAchievements().stream()
                .map(UserAchievementMapper::toResponse)
                .collect(Collectors.toList());
    }
}

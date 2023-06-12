package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.UserStatusResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.UserStatusMapper.toResponse;

@Service
public class UserStatusService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public UserStatusResponse get() {
        Usuario usuario = usuarioAutenticadoService.get();
        return toResponse(usuario);
    }
}

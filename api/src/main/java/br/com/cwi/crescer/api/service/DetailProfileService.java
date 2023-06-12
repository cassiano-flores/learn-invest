package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ProfileResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.DetailProfileMapper.toResponse;

@Service
public class DetailProfileService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public ProfileResponse detail() {
        Usuario usuario = usuarioAutenticadoService.get();

        return toResponse(usuario);
    }

}

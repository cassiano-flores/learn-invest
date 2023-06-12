package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.mapper.IconMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileIconService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public List<IconResponse> list() {

        Usuario usuario = usuarioAutenticadoService.get();
        return usuario.getIcons().stream().map(IconMapper::toResponse).collect(Collectors.toList());
    }
}

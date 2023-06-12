package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ProfileEditedRequest;
import br.com.cwi.crescer.api.controller.response.ProfileEditedResponse;
import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.mapper.ProfileEditMapper.toResponse;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ProfileEditService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchIconService searchIconService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public ProfileEditedResponse edit(ProfileEditedRequest request) {

        Usuario usuario = usuarioAutenticadoService.get();
        Icon icon = searchIconService.byId(request.getIconId());

        if(!usuario.getIcons().contains(icon)) {
            throw new ResponseStatusException(BAD_REQUEST, "User don't own required icon");
        }

        usuario.setName(request.getName());
        usuario.setNickname(request.getNickname());
        usuario.setCurrentIcon(icon);

        usuarioRepository.save(usuario);
        return toResponse(usuario);
    }
}

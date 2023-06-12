package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ProfileSummaryResponse;
import br.com.cwi.crescer.api.mapper.ProfileSummaryMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario byId(Long userId) {

        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found for this ID"));
    }

    public Usuario byEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found for this email"));
    }

    public Usuario byToken(String token) {

        return usuarioRepository.findByToken(token)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found for this token"));
    }

    public Page<ProfileSummaryResponse> byNameOrEmailContains(String text, Pageable pageable) {
        return  usuarioRepository.findByNameContainsIgnoreCaseOrEmailContainsIgnoreCase(text, text, pageable)
                .map(ProfileSummaryMapper::toResponse);
    }
}

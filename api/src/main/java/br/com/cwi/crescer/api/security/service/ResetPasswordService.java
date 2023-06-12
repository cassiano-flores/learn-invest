package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.controller.request.ResetPasswordRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.service.SearchUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.security.mapper.UsuarioMapper.toResponse;

@Service
public class ResetPasswordService {

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioResponse reset(ResetPasswordRequest request, String token) {

        Usuario usuario = searchUsuarioService.byToken(token);

        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setToken(null);

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }
}

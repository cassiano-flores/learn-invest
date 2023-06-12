package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.security.UsuarioSecurity;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.service.SearchUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class BuscarUsuarioSecurityService implements UserDetailsService {

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = searchUsuarioService.byEmail(email);
        return new UsuarioSecurity(usuario);
    }
}

package br.com.cwi.crescer.api.security;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioSecurity implements UserDetails {

    private static final String PREFIXO_PERMISSAO_SPRING = "ROLE_";

    private final Long id;
    private final String email;
    private final String password;
    private final boolean active;
    private final List<SimpleGrantedAuthority> permissions;

    public UsuarioSecurity(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.password = usuario.getPassword();
        this.active = usuario.isActive();
        this.permissions = permissionsConverter(usuario);
    }

    private List<SimpleGrantedAuthority> permissionsConverter(Usuario usuario) {
        return usuario.getPermissions().stream()
                .map(permissao -> new SimpleGrantedAuthority(PREFIXO_PERMISSAO_SPRING + permissao.getName()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    @Override
    public List<SimpleGrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.request.ResetPasswordRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.ResetPasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ResetPasswordServiceTest {

    @InjectMocks
    private ResetPasswordService tested;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve trocar senha e alterar token para null com sucesso")
    void DeveTrocarSenhaEAlterarTokenParaNullComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        String token = usuario.getToken();

        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword("123");

        when(searchUsuarioService.byToken(token)).thenReturn(usuario);

        tested.reset(request, token);

        verify(searchUsuarioService).byToken(token);
        verify(passwordEncoder).encode(request.getPassword());
        verify(usuarioRepository).save(usuario);

        assertNull(usuario.getToken());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        Usuario usuario = UsuarioFactory.get();
        String token = usuario.getToken();

        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword("123");

        when(searchUsuarioService.byToken(token)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found with this token"));

        assertThrows(ResponseStatusException.class, () -> tested.reset(request, token));

        verify(searchUsuarioService).byToken(token);
    }
}

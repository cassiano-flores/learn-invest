package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.controller.request.ForgotPasswordRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.ForgotPasswordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordServiceTest {

    @InjectMocks
    private ForgotPasswordService tested;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private Environment environment;

    @Test
    @DisplayName("Deve salvar token e enviar email com sucesso")
    void deveSalvarTokenEEnviarEmailComSucesso() throws MessagingException {

        Usuario usuario = UsuarioFactory.get();
        String email = usuario.getEmail();

        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail(email);

        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(environment.getProperty(any())).thenReturn("test");
        doNothing().when(javaMailSender).send(mimeMessage);

        when(searchUsuarioService.byEmail(email)).thenReturn(usuario);

        tested.generateResetToken(request);

        verify(searchUsuarioService).byEmail(email);
        verify(usuarioRepository).save(usuario);

        assertNotNull(usuario.getToken());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        Usuario usuario = UsuarioFactory.get();
        String email = usuario.getEmail();

        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail(email);

        when(searchUsuarioService.byEmail(email)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.generateResetToken(request));

        verify(searchUsuarioService).byEmail(email);
    }
}

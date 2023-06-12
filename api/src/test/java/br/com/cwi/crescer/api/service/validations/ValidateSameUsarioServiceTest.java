package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidateSameUsarioServiceTest {

    @InjectMocks
    private ValidateSameUsarioService tested;

    @Test
    @DisplayName("Deve lançar exceção quando o sender e receiver forem o mesmo usuário mandando challenge")
    void deveLancarExcecaoQuandoSenderEReceiverDeMesmoIdSend() {

        Usuario usuario = UsuarioFactory.get();
        Long senderId = usuario.getId();
        Long receiverId = usuario.getId();

        assertThrows(ResponseStatusException.class, () -> tested.validateSend(senderId, receiverId));
        assertThrows(ResponseStatusException.class, () -> tested.validateSend(receiverId, senderId));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o sender e receiver forem o mesmo usuário respondendo challenge")
    void deveLancarExcecaoQuandoSenderEReceiverDeMesmoIdReply() {

        Usuario usuario = UsuarioFactory.get();
        Long senderId = usuario.getId();
        Long receiverId = usuario.getId();

        assertThrows(ResponseStatusException.class, () -> tested.validateReply(senderId, receiverId));
        assertThrows(ResponseStatusException.class, () -> tested.validateReply(receiverId, senderId));
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o sender e receiver forem usuários diferentes mandando challenge")
    void naoDeveLancarExcecaoQuandoSenderEReceiverDeIdsDiferentesSend() {
        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        tested.validateSend(senderId, receiverId);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o sender e receiver forem usuários diferentes respondendo challenge")
    void naoDeveLancarExcecaoQuandoSenderEReceiverDeIdsDiferentesReply() {
        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        tested.validateReply(receiverId, senderId);
    }
}

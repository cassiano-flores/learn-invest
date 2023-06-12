package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.UsuarioChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CONFLICT;

@ExtendWith(MockitoExtension.class)
public class ValidateExistingChallengeRequestServiceTest {

    @InjectMocks
    private ValidateExistingChallengeRequestService tested;

    @Mock
    private UsuarioChallengeRepository usuarioChallengeRepository;

    @Test
    @DisplayName("Deve lançar exceção quando já houver uma solicitação de Challenge em aberto")
    void deveLancarExcecaoQuandoJaTiverSolicitacaoDeChallengeAberta() {

        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        when(usuarioChallengeRepository.existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(senderId, receiverId))
                .thenReturn(true);
        when(usuarioChallengeRepository.existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(receiverId, senderId))
                .thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> tested.validate(senderId, receiverId));
        assertThrows(ResponseStatusException.class, () -> tested.validate(receiverId, senderId));

        verify(usuarioChallengeRepository).existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(senderId, receiverId);
        verify(usuarioChallengeRepository).existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(receiverId, senderId);
    }

    @Test
    @DisplayName("Não deve lançar exceção quando não houver solicitação de Challenge em aberto")
    void naoDeveLancarExcecaoQuandoNaoHouverSolicitacaoDeChallengeAberta() {

        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        when(usuarioChallengeRepository.existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(senderId, receiverId))
                .thenReturn(false);
        when(usuarioChallengeRepository.existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(receiverId, senderId))
                .thenReturn(false);

        assertDoesNotThrow(() -> tested.validate(senderId, receiverId));
        assertDoesNotThrow(() -> tested.validate(receiverId, senderId));

        verify(usuarioChallengeRepository).existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(senderId, receiverId);
        verify(usuarioChallengeRepository).existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(receiverId, senderId);
    }

    @Test
    @DisplayName("Deve lançar exceção com mensagem de erro quando já houver uma solicitação de Challenge em aberto")
    void deveLancarExcecaoComMensagemDeErroQuandoJaTiverSolicitacaoDeChallengeAberta() {

        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        when(usuarioChallengeRepository.existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(senderId, receiverId))
                .thenThrow(new ResponseStatusException(CONFLICT, "There is already a open challenge with this users"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tested.validate(senderId, receiverId));

        assertEquals("There is already a open challenge with this users", exception.getReason());

        verify(usuarioChallengeRepository).existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse(senderId, receiverId);
    }
}

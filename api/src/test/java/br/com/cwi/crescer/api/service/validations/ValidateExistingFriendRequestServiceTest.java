package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateExistingFriendRequestServiceTest {

    @InjectMocks
    private ValidateExistingFriendRequestService tested;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Test
    @DisplayName("Deve lançar exceção ao checar uma requisição já existente")
    void deveLancarExcecaoAoChecarRequisicaoExistente() {

        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        FriendRequest friendRequest = FriendRequest.builder()
                .usuarioSender(sender)
                .usuarioReceiver(receiver)
                .build();

        when(friendRequestRepository.findExistingFriendRequest(senderId, receiverId))
                .thenReturn(Optional.ofNullable(friendRequest));

        assertThrows(ResponseStatusException.class, () -> tested.validate(senderId, receiverId));

        verify(friendRequestRepository).findExistingFriendRequest(senderId, receiverId);
    }

    @Test
    @DisplayName("Não deve lançar exceção ao checar uma requisição inexistente")
    void naoDeveLancarExcecaoAoChecarRequisicaoInexistente() {

        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        when(friendRequestRepository.findExistingFriendRequest(senderId, receiverId))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> tested.validate(senderId, receiverId));

        verify(friendRequestRepository).findExistingFriendRequest(senderId, receiverId);
    }
}

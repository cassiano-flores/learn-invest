package br.com.cwi.crescer.api.service;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchFriendRequestServiceTest {

    @InjectMocks
    private SearchFriendRequestService tested;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Test
    @DisplayName("Deve retornar a requisicao de amizade designada")
    void deveRetornarRequisicaoDesignada() {

        Usuario usuario = UsuarioFactory.get();
        Usuario sender = UsuarioFactory.get();

        FriendRequest request = new FriendRequest();
        request.setUsuarioReceiver(usuario);
        request.setUsuarioSender(sender);

        when(friendRequestRepository.findByUsuarioSenderIdAndUsuarioReceiverId(sender.getId(), usuario.getId()))
                .thenReturn(Optional.of(request));

        FriendRequest response = tested.findByUsersId(sender.getId(), usuario.getId());

        verify(friendRequestRepository).findByUsuarioSenderIdAndUsuarioReceiverId(sender.getId(), usuario.getId());

        assertEquals(request.getId(), response.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando requisição de amizade não existir")
    void deveLancarExcecaoQuandoSolicitacaoNaoExistir() {

        Usuario usuario = UsuarioFactory.get();
        Usuario friend = UsuarioFactory.get();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.findByUsersId(friend.getId(), usuario.getId()));

        assertEquals("Friendship request not found", exception.getReason());
    }
}

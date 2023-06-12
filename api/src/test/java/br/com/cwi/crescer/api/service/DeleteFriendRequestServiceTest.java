package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.factories.FriendRequestFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class DeleteFriendRequestServiceTest {

    @InjectMocks
    private DeleteFriendRequestService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private SearchFriendRequestService searchFriendRequestService;

    @Test
    @DisplayName("Deve excluir a solicitação de amizade com sucesso")
    void deveExcluirSolicitacaoDeAmizadeComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioSender = UsuarioFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioSenderId = usuarioSender.getId();
        FriendRequest friendRequest = FriendRequestFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchFriendRequestService.findByUsersId(usuarioSenderId, usuarioId)).thenReturn(friendRequest);

        tested.delete(usuarioSenderId);

        verify(usuarioAutenticadoService).get();
        verify(searchFriendRequestService).findByUsersId(usuarioSenderId, usuarioId);
        verify(friendRequestRepository).delete(friendRequest);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário remetente não for encontrado")
    void deveLancarExcecaoQuandoRemetenteNaoForEncontrado() {

        Usuario usuarioSender = UsuarioFactory.get();
        Long usuarioSenderId = usuarioSender.getId();

        when(usuarioAutenticadoService.get()).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.delete(usuarioSenderId));

        verify(usuarioAutenticadoService).get();
    }

    @Test
    @DisplayName("Deve lançar exceção quando request não for encontrada")
    void deveLancarExcecaoQuandoRequestNaoForEncontrada() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioSender = UsuarioFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioSenderId = usuarioSender.getId();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchFriendRequestService.findByUsersId(usuarioSenderId, usuarioId)).thenThrow
                (new ResponseStatusException(NOT_FOUND, "Friendship request not found"));

        assertThrows(ResponseStatusException.class, () -> tested.delete(usuarioSenderId));

        verify(usuarioAutenticadoService).get();
        verify(searchFriendRequestService).findByUsersId(usuarioSenderId, usuarioId);
    }
}

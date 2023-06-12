package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AcceptFriendRequestServiceTest {

    @InjectMocks
    private AcceptFriendRequestService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private SearchFriendRequestService searchFriendRequestService;

    @Captor
    private ArgumentCaptor<Friendship> friendshipCaptor;

    @Test
    @DisplayName("Deve criar Friendship")
    void deveCriarFriendshipParaUsuario() {

        Usuario authenticated = UsuarioFactory.get();
        authenticated.setId(1L);

        Usuario sender = UsuarioFactory.get();
        sender.setId(2L);

        FriendRequest friendRequest = FriendRequest.builder()
                .id(getRandomLong())
                .usuarioReceiver(authenticated)
                .usuarioSender(sender)
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(authenticated);
        when(searchUsuarioService.byId(sender.getId())).thenReturn(sender);
        when(searchFriendRequestService.findByUsersId(sender.getId(), authenticated.getId()))
                .thenReturn(friendRequest);

        tested.accept(sender.getId());

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(sender.getId());
        verify(searchFriendRequestService).findByUsersId(sender.getId(), authenticated.getId());
        verify(friendshipRepository, times(2)).save(friendshipCaptor.capture());
        verify(friendRequestRepository).delete(friendRequest);

        assertEquals(authenticated.getFriends().size(), 1);
        assertEquals(sender.getFriends().size(), 1);
    }
}

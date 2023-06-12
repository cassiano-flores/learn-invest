package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.FriendshipFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteFriendshipServiceTest {

    @InjectMocks
    private DeleteFriendshipService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private SearchFriendshipService searchFriendshipService;

    @Test
    @DisplayName("Deve remover amizade")
    void deveRemoverAmizade() {

        Usuario authenticated = UsuarioFactory.get();
        authenticated.setId(1L);

        Usuario friend = UsuarioFactory.get();
        friend.setId(2L);

        Friendship authenticatedFriendship = FriendshipFactory.get();
        authenticatedFriendship.setUsuarioId(authenticated.getId());
        authenticatedFriendship.setFriend(friend);
        authenticated.getFriends().add(authenticatedFriendship);

        Friendship friendFriendship = FriendshipFactory.get();
        friendFriendship.setUsuarioId(friend.getId());
        friendFriendship.setFriend(authenticated);
        friend.getFriends().add(friendFriendship);

        when(usuarioAutenticadoService.get()).thenReturn(authenticated);
        when(searchUsuarioService.byId(friend.getId())).thenReturn(friend);
        when(searchFriendshipService.searchByUsersIds(authenticated.getId(), friend.getId()))
                .thenReturn(authenticatedFriendship);
        when(searchFriendshipService.searchByUsersIds(friend.getId(), authenticated.getId()))
                .thenReturn(friendFriendship);

        tested.delete(friend.getId());

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(friend.getId());
        verify(searchFriendshipService).searchByUsersIds(authenticated.getId(), friend.getId());
        verify(searchFriendshipService).searchByUsersIds(friend.getId(), authenticated.getId());
        verify(friendshipRepository).delete(authenticatedFriendship);
        verify(friendshipRepository).delete(friendFriendship);

        assertEquals(authenticated.getFriends().size(), 0);
        assertEquals(friend.getFriends().size(), 0);
    }
}

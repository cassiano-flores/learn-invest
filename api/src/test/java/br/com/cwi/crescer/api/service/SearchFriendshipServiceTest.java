package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.FriendshipFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
public class SearchFriendshipServiceTest {

    @InjectMocks
    private SearchFriendshipService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Test
    @DisplayName("Deve retornar amizade entre usuários")
    void deveRetornarAmizadeEntreUsuarios() {

        Usuario authenticated = UsuarioFactory.get();
        authenticated.setId(1L);

        Usuario friend = UsuarioFactory.get();
        friend.setId(2L);

        Friendship friendship = FriendshipFactory.get();
        friendship.setUsuarioId(authenticated.getId());
        friendship.setFriend(friend);

        when(friendshipRepository.findByUsuarioIdAndFriendId(authenticated.getId(), friend.getId()))
                .thenReturn(Optional.of(friendship));

        Friendship result = tested.searchByUsersIds(authenticated.getId(), friend.getId());

        verify(friendshipRepository).findByUsuarioIdAndFriendId(authenticated.getId(), friend.getId());

        assertEquals(friendship.getUsuarioId(), result.getUsuarioId());
        assertEquals(friendship.getFriend().getId(), result.getFriend().getId());
    }

    @Test
    @DisplayName("Deve retornar exceção quando amizade não existe")
    void deveRetornarExcecaoQuandoAmizadeInexiste() {

        Usuario authenticated = UsuarioFactory.get();
        authenticated.setId(1L);

        Usuario friend = UsuarioFactory.get();
        friend.setId(2L);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.searchByUsersIds(authenticated.getId(), friend.getId()));

        assertEquals("Friendship or users don´t exist", exception.getReason());
    }

    public Friendship searchByUsersIds(Long userId, Long friendId) {

        return friendshipRepository.findByUsuarioIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,
                        "Friendship or users don´t exist"));
    }
}

package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateFriendshipExistanceServiceTest {

    @InjectMocks
    private ValidateFriendshipExistanceService tested;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Test
    @DisplayName("Deve lançar exceção ao checar se uma amizade já existe")
    void deveLancarExcecaoAoChecarSeUmaAmizadeJaExiste() {

        Usuario usuario = UsuarioFactory.get();
        usuario.setId(1L);
        Usuario friend = UsuarioFactory.get();
        friend.setId(2L);

        Friendship friendship = Friendship.builder()
                .id(getRandomLong())
                .usuarioId(usuario.getId())
                .friend(friend)
                .build();

        Friendship sameFriendship = Friendship.builder()
                .id(getRandomLong())
                .usuarioId(friend.getId())
                .friend(usuario)
                .build();

        usuario.getFriends().add(friendship);
        friend.getFriends().add(sameFriendship);

        when(friendshipRepository.findByUsuarioIdAndFriendId(usuario.getId(), friend.getId()))
                .thenReturn(Optional.ofNullable(friendship));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.existbyIds(usuario.getId(), friend.getId()));

        assertEquals("This friendship already exists!", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando a amizade não existe")
    void naoDeveLancarExcecaoQuandoAmizadeNaoExiste() {

        Usuario usuario = UsuarioFactory.get();
        Usuario friend = UsuarioFactory.get();

        when(friendshipRepository.findByUsuarioIdAndFriendId(usuario.getId(), friend.getId()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> tested.existbyIds(usuario.getId(), friend.getId()));
    }
}

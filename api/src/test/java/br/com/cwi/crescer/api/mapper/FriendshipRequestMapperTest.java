package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FriendshipRequestMapperTest {

    @Test
    @DisplayName("Deve mapear corretamente uma FriendshipRequest para FriendshipResponse")
    void DeveMapearCorretamenteUmaFriendshipRequestParaFriendshipResponse(){

        Usuario friend = UsuarioFactory.get();
        FriendshipResponse response = FriendshipRequestMapper.toResponse(friend);

        assertEquals(response.getId(), friend.getId());
        assertEquals(response.getName(), friend.getName());
        assertEquals(response.getNickname(), friend.getNickname());
        assertEquals(response.getLeagueTitle(), friend.getLeagueTitle());
        assertEquals(response.getLeaguePoints(), friend.getLeaguePoints());
        assertEquals(response.getXp(), friend.getXp());
        assertEquals(response.getCurrentIcon().getId(), friend.getCurrentIcon().getId());
    }
}

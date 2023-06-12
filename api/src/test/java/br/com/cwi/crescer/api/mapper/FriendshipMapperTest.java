package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.FriendshipFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FriendshipMapperTest {

    @Test
    @DisplayName("Deve mapear corretamente uma Friendship para FriendSshipResponse")
    void DeveMapearCorretamenteUmaFriendshipParaFriendshipResponse(){

        Friendship friend = FriendshipFactory.get();
        FriendshipResponse response = FriendshipMapper.toResponse(friend);

        assertEquals(response.getName(), friend.getFriend().getName());
        assertEquals(response.getLeaguePoints(), friend.getFriend().getLeaguePoints());
        assertEquals(response.getCurrentIcon().getName(), friend.getFriend().getCurrentIcon().getName());
        assertEquals(response.getLeaguePoints(), friend.getFriend().getLeaguePoints());
        assertEquals(response.getXp(), friend.getFriend().getXp());
        assertEquals(response.getLeagueTitle(), friend.getFriend().getLeagueTitle());
    }
}

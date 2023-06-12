package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListChallengeMapperTest {

    @Test
    @DisplayName("Deve mapear um Challenge para ListChallengeResponse")
    void deveMapearChallengeParaListChallengeResponse() {

        Challenge challenge = ChallengeFactory.get();

        ListChallengeResponse response = ListChallengeMapper.toResponse(challenge);

        assertEquals(response.getId(), challenge.getId());
        assertEquals(response.getUsuarioSenderId(), challenge.getUsuarioChallenge().getUsuarioSender().getId());
        assertEquals(response.getUsuarioReceiverId(), challenge.getUsuarioChallenge().getUsuarioReceiver().getId());
        assertEquals(response.isComplete(), challenge.isComplete());
    }
}

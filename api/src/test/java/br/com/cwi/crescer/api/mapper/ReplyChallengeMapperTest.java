package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ReplyChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReplyChallengeMapperTest {

    @Test
    @DisplayName("Deve mapear um Challenge para ReplyChallengeResponse")
    void deveMapearChallengeParaReplyChallengeResponse() {

        Challenge challenge = ChallengeFactory.get();

        ReplyChallengeResponse response = ReplyChallengeMapper.toResponse(challenge);

        assertEquals(response.getChallengeId(), challenge.getId());
    }
}

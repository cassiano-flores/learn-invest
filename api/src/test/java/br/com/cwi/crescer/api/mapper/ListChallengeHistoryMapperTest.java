package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListChallengeHistoryResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListChallengeHistoryMapperTest {

    @Test
    @DisplayName("Deve mapear um Challenge para ListChallengeHistoryResponse")
    void deveMapearChallengeParaListChallengeHistoryResponse() {

        Challenge challenge = ChallengeFactory.get();

        ListChallengeHistoryResponse response = ListChallengeHistoryMapper.toResponse(challenge);

        assertEquals(response.getCurrentUsuarioScore(), challenge.getUsuarioSenderScore());
        assertEquals(response.getOpponentUsuarioScore(), challenge.getUsuarioReceiverScore());
        assertEquals(response.getChallengedIn(), challenge.getChallengedIn());
    }
}

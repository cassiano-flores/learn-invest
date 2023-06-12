package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListChallengeHistoryResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListChallengeHistoryMapper {

    public static ListChallengeHistoryResponse toResponse(Challenge challenge) {

        return ListChallengeHistoryResponse.builder()
                .currentUsuario(UsuarioChallengeHistoryMapper.toResponse(challenge.getUsuarioChallenge().getUsuarioSender()))
                .opponentUsuario(UsuarioChallengeHistoryMapper.toResponse(challenge.getUsuarioChallenge().getUsuarioReceiver()))
                .currentUsuarioScore(challenge.getUsuarioSenderScore())
                .opponentUsuarioScore(challenge.getUsuarioReceiverScore())
                .challengedIn(challenge.getChallengedIn())
                .build();
    }
}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListChallengeMapper {

    public static ListChallengeResponse toResponse(Challenge challenge) {

        return ListChallengeResponse.builder()
                .id(challenge.getId())
                .usuarioSenderId(challenge.getUsuarioChallenge().getUsuarioSender().getId())
                .usuarioReceiverId(challenge.getUsuarioChallenge().getUsuarioReceiver().getId())
                .isComplete(challenge.isComplete())
                .build();
    }
}

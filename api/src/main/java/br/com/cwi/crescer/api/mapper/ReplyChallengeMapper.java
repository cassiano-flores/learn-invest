package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ReplyChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyChallengeMapper {

    public static ReplyChallengeResponse toResponse(Challenge challenge) {

        return ReplyChallengeResponse.builder()
                .challengeId(challenge.getId())
                .build();
    }
}

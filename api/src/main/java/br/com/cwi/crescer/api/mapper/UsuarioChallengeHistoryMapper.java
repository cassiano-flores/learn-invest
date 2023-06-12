package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UsuarioChallengeHistoryResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsuarioChallengeHistoryMapper {

    public static UsuarioChallengeHistoryResponse toResponse(Usuario usuarioSender) {

        return UsuarioChallengeHistoryResponse.builder()
                .id(usuarioSender.getId())
                .name(usuarioSender.getName())
                .nickname(usuarioSender.getNickname())
                .currentIconId(usuarioSender.getCurrentIcon().getId())
                .build();
    }
}

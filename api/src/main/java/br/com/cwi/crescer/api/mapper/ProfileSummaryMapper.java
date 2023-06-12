package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ProfileSummaryResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileSummaryMapper {

    public static ProfileSummaryResponse toResponse(Usuario usuario) {

        return ProfileSummaryResponse.builder()
                .id(usuario.getId())
                .name(usuario.getName())
                .nickname(usuario.getNickname())
                .currentIcon(IconMapper.toResponse(usuario.getCurrentIcon()))
                .build();
    }
}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ProfileEditedResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileEditMapper {

    public static ProfileEditedResponse toResponse(Usuario usuario) {

        return ProfileEditedResponse.builder()
                .name(usuario.getName())
                .nickname(usuario.getNickname())
                .currentIcon(IconMapper.toResponse(usuario.getCurrentIcon()))
                .build();
    }
}

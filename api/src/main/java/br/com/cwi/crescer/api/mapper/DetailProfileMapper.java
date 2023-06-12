package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ProfileResponse;
import br.com.cwi.crescer.api.security.domain.Permissao;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailProfileMapper {

    public static ProfileResponse toResponse(Usuario usuario) {

        return ProfileResponse.builder()
                .id(usuario.getId())
                .name(usuario.getName())
                .permissions(usuario.getPermissions().stream().map(Permissao::getName).collect(toList()))
                .nickname(usuario.getNickname())
                .email(usuario.getEmail())
                .createIn(usuario.getCreateIn())
                .updateIn(usuario.getUpdateIn())
                .currentIcon(IconMapper.toResponse(usuario.getCurrentIcon()))
                .courses(usuario.getCourses().stream().map(CourseMapper::toResponse).collect(toList()))
                .finishedActivities(usuario.getFinishedActivities().stream().map(ActivityMapper::toResponse).collect(toList()))
                .build();
    }
}

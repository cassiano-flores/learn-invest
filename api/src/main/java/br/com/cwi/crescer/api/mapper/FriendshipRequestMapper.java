package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendshipRequestMapper {

    public static FriendshipResponse toResponse(Usuario entity) {

        return FriendshipResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .leagueTitle(entity.getLeagueTitle())
                .leaguePoints(entity.getLeaguePoints())
                .xp(entity.getXp())
                .currentIcon(IconMapper.toResponse(entity.getCurrentIcon()))
                .build();
    }
}

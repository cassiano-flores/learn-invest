package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FriendshipMapper {

    public static FriendshipResponse toResponse(Friendship friendship) {

        return FriendshipResponse.builder()
                .id(friendship.getFriend().getId())
                .name(friendship.getFriend().getName())
                .nickname(friendship.getFriend().getNickname())
                .email(friendship.getFriend().getEmail())
                .currentIcon(IconMapper.toResponse(friendship.getFriend().getCurrentIcon()))
                .xp(friendship.getFriend().getXp())
                .leaguePoints(friendship.getFriend().getLeaguePoints())
                .leagueTitle(friendship.getFriend().getLeagueTitle())
                .build();
    }

    public static FriendshipResponse toEntity(Usuario entity) {

        return FriendshipResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .currentIcon(IconMapper.toResponse(entity.getCurrentIcon()))
                .xp(entity.getXp())
                .leaguePoints(entity.getLeaguePoints())
                .leagueTitle(entity.getLeagueTitle())
                .build();
    }
}

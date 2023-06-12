package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UserStatusResponse;
import br.com.cwi.crescer.api.security.domain.Usuario;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserStatusMapper {

    public static UserStatusResponse toResponse(Usuario entity) {

        return UserStatusResponse.builder()
                .coins(entity.getCoins())
                .xp(entity.getXp())
                .leaguePoints(entity.getLeaguePoints())
                .leagueTitle(entity.getLeagueTitle())
                .build();
    }
}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UserAchievementResponse;
import br.com.cwi.crescer.api.domain.Achievement;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAchievementMapper {

    public static UserAchievementResponse toResponse(Achievement entity) {

        return UserAchievementResponse.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .build();
    }
}

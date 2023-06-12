package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ActivityResponse;
import br.com.cwi.crescer.api.domain.Activity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListActivityMapper {

    public static ActivityResponse toResponse(Activity activity) {

        return ActivityResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .build();
    }
}

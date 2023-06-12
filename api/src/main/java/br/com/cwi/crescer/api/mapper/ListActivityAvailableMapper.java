package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ActivityAvailableResponse;
import br.com.cwi.crescer.api.domain.Activity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListActivityAvailableMapper {

    public static ActivityAvailableResponse toResponse(Activity activity) {

        return ActivityAvailableResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .lesson(activity.getLesson())
                .courseName(activity.getCourse().getName())
                .build();
    }
}

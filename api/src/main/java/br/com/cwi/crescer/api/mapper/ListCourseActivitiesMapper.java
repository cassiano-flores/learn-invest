package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListCourseActivitiesResponse;
import br.com.cwi.crescer.api.domain.Activity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListCourseActivitiesMapper {

    public static ListCourseActivitiesResponse toResponse(Activity activity) {

        return ListCourseActivitiesResponse.builder()
                .id(activity.getId())
                .title(activity.getTitle())
                .build();
    }
}

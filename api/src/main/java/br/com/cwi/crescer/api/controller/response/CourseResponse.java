package br.com.cwi.crescer.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponse {

    private Long id;
    private String name;
    private Long nextCourseId;
    private Long achievementCompleteCourseId;
    private List<ActivityResponse> activities = new ArrayList<>();
}

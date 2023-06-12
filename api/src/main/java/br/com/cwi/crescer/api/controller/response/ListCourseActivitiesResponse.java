package br.com.cwi.crescer.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListCourseActivitiesResponse {

    private Long id;
    private String title;
    private Long score;
}

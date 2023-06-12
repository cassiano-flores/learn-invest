package br.com.cwi.crescer.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityAvailableResponse {

    private Long id;
    private String title;
    private String lesson;
    private String courseName;
    private Long score;
}

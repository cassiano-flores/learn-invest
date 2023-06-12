package br.com.cwi.crescer.api.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponse {

    private Long id;
    private String name;
    private List<String> permissions;
    private String nickname;
    private String email;

    @JsonFormat(pattern = "MMMM YYYY")
    private LocalDate createIn;

    @JsonFormat(pattern = "MMMM YYYY")
    private LocalDate updateIn;

    private IconResponse currentIcon;
    private List<CourseResponse> courses = new ArrayList<>();
    private List<ActivityResponse> finishedActivities = new ArrayList<>();
}

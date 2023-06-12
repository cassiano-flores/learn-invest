package br.com.cwi.crescer.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileSummaryResponse {

    private Long id;
    private String name;
    private String nickname;
    private IconResponse currentIcon;
}

package br.com.cwi.crescer.api.controller.response;

import br.com.cwi.crescer.api.domain.enums.LeagueTitle;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendshipResponse {

    private Long id;
    private String name;
    private String nickname;
    private String email;
    private LeagueTitle leagueTitle;
    private Long leaguePoints;
    private Long xp;
    private IconResponse currentIcon;
}

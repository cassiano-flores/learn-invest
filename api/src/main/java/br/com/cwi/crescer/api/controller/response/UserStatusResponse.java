package br.com.cwi.crescer.api.controller.response;


import br.com.cwi.crescer.api.domain.enums.LeagueTitle;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserStatusResponse {

    private Long leaguePoints;
    private Long coins;
    private Long xp;
    private LeagueTitle leagueTitle;
}

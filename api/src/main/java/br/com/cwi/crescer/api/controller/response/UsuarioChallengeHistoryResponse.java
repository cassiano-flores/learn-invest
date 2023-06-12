package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioChallengeHistoryResponse {

    private Long id;
    private String name;
    private String nickname;
    private Long currentIconId;
}

package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ListChallengeResponse {

    private Long id;
    private Long usuarioSenderId;
    private Long usuarioReceiverId;
    private boolean isComplete;
}

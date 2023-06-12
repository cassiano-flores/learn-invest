package br.com.cwi.crescer.api.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ListChallengeHistoryResponse {

    private UsuarioChallengeHistoryResponse currentUsuario;
    private UsuarioChallengeHistoryResponse opponentUsuario;
    private Long currentUsuarioScore;
    private Long opponentUsuarioScore;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime challengedIn;
}

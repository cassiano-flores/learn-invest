package br.com.cwi.crescer.api.controller.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResultActivityRequest {

    private Long score;
}

package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ListActivityQuestionsResponse {

    private Long id;
    private String questionText;
    private String alternativeA;
    private String alternativeB;
    private String alternativeC;
    private String alternativeD;
}

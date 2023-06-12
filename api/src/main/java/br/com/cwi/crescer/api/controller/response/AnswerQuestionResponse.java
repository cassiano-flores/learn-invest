package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AnswerQuestionResponse {

    private boolean isCorrect;
}

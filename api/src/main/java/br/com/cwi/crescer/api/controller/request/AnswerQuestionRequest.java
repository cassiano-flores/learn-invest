package br.com.cwi.crescer.api.controller.request;

import br.com.cwi.crescer.api.domain.enums.AnswerOptions;
import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AnswerQuestionRequest {

    @NotNull
    private AnswerOptions answer;
}

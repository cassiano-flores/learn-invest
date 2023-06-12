package br.com.cwi.crescer.api.controller.request;

import br.com.cwi.crescer.api.domain.enums.AnswerOptions;
import lombok.*;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IncludeQuestionRequest {

    @NotNull
    private Long activityId;

    @NotBlank
    private String questionText;

    @NotBlank
    private String alternativeA;

    @NotBlank
    private String alternativeB;

    @NotBlank
    private String alternativeC;

    @NotBlank
    private String alternativeD;

    @NotNull
    @Enumerated(STRING)
    private AnswerOptions answer;
}

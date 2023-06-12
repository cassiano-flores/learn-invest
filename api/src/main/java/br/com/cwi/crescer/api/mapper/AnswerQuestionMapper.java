package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.AnswerQuestionResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnswerQuestionMapper {

    public static AnswerQuestionResponse toResponse(boolean isCorrect) {

        return AnswerQuestionResponse.builder()
                .isCorrect(isCorrect)
                .build();
    }
}

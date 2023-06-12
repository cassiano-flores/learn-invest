package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.domain.Question;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListActivityQuestionsMapper {

    public static ListActivityQuestionsResponse toResponse(Question question) {

        return ListActivityQuestionsResponse.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .alternativeA(question.getAlternativeA())
                .alternativeB(question.getAlternativeB())
                .alternativeC(question.getAlternativeC())
                .alternativeD(question.getAlternativeD())
                .build();
    }
}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.IncludeQuestionRequest;
import br.com.cwi.crescer.api.controller.response.QuestionResponse;
import br.com.cwi.crescer.api.domain.Question;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionMapper {

    public static Question toEntity(IncludeQuestionRequest request) {
        Question entity = new Question();
        entity.setQuestionText(request.getQuestionText());
        entity.setAlternativeA(request.getAlternativeA());
        entity.setAlternativeB(request.getAlternativeB());
        entity.setAlternativeC(request.getAlternativeC());
        entity.setAlternativeD(request.getAlternativeD());
        entity.setAnswer(request.getAnswer());
        return entity;
    }

    public static QuestionResponse toResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .build();
    }
}

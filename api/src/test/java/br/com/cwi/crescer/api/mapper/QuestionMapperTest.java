package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.request.IncludeQuestionRequest;
import br.com.cwi.crescer.api.controller.response.QuestionResponse;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.factories.QuestionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.cwi.crescer.api.domain.enums.AnswerOptions.A;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionMapperTest {

    @Test
    @DisplayName("Deve criar uma nova Question entity")
    void deveCriarQuestionEntity() {

        IncludeQuestionRequest request = new IncludeQuestionRequest();
        request.setQuestionText("Test");
        request.setAlternativeA("Test");
        request.setAlternativeB("Test");
        request.setAlternativeC("Test");
        request.setAlternativeD("Test");
        request.setAnswer(A);

        Question entity = QuestionMapper.toEntity(request);

        assertEquals(request.getQuestionText(), entity.getQuestionText());
        assertEquals(request.getAlternativeA(), entity.getAlternativeA());
        assertEquals(request.getAlternativeB(), entity.getAlternativeB());
        assertEquals(request.getAlternativeC(), entity.getAlternativeC());
        assertEquals(request.getAlternativeD(), entity.getAlternativeD());
        assertEquals(request.getAnswer(), entity.getAnswer());
    }

    @Test
    @DisplayName("Deve mapear uma Question para QuestionResponse")
    void deveMapearQuestionParaQuestionResponse() {

        Question question = QuestionFactory.get();

        QuestionResponse response = QuestionMapper.toResponse(question);

        assertEquals(response.getId(), question.getId());
    }
}

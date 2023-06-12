package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.factories.QuestionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListActivityQuestionsMapperTest {

    @Test
    @DisplayName("Deve mapear uma Questão para ListActivityQuestionsResponse")
    void deveMapearQuestaoParaListActivityQuestionsResponse() {

        Question question = QuestionFactory.get();

        ListActivityQuestionsResponse response = ListActivityQuestionsMapper.toResponse(question);

        assertEquals(response.getId(), question.getId());
        assertEquals(response.getQuestionText(), question.getQuestionText());
        assertEquals(response.getAlternativeA(), question.getAlternativeA());
        assertEquals(response.getAlternativeB(), question.getAlternativeB());
        assertEquals(response.getAlternativeC(), question.getAlternativeC());
        assertEquals(response.getAlternativeD(), question.getAlternativeD());
    }
}

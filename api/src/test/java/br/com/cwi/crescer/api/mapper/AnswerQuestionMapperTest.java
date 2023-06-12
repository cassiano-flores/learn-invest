package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.AnswerQuestionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerQuestionMapperTest {

    @Test
    @DisplayName("Deve mapear isCorrect para AnswerQuestionResponse")
    void deveMapearIsCorrectParaAnswerQuestionResponse() {

        Random random = new Random();
        boolean booleanValue = random.nextBoolean();

        AnswerQuestionResponse response = AnswerQuestionMapper.toResponse(booleanValue);

        assertEquals(response.isCorrect(), booleanValue);
    }
}

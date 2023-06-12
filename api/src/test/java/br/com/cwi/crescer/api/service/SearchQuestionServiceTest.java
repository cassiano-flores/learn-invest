package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.factories.QuestionFactory;
import br.com.cwi.crescer.api.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class SearchQuestionServiceTest {

    @InjectMocks
    private SearchQuestionService tested;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("Deve retornar questão com sucesso")
    void deveRetornarQuestaoComSucesso() {

        Question question = QuestionFactory.get();
        Long questionId = question.getId();

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(question));

        Question questionExpected = tested.byId(questionId);

        verify(questionRepository).findById(questionId);

        assertEquals(questionExpected, question);
    }

    @Test
    @DisplayName("Deve lançar exceção quando questão não for encontrada")
    void deveLancarExcecaoQuandoQuestaoNaoForEncontrada() {

        Question question = QuestionFactory.get();
        Long questionId = question.getId();

        when(questionRepository.findById(questionId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Question not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.byId(questionId));

        verify(questionRepository).findById(questionId);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AnswerQuestionRequest;
import br.com.cwi.crescer.api.controller.response.AnswerQuestionResponse;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.factories.QuestionFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static br.com.cwi.crescer.api.domain.enums.AnswerOptions.A;
import static br.com.cwi.crescer.api.domain.enums.AnswerOptions.B;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class AnswerQuestionServiceTest {

    @InjectMocks
    private AnswerQuestionService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchQuestionService searchQuestionService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar true quando a resposta estiver correta")
    void deveRetornarTrueQuandoRespostaEstiverCorreta() {

        Usuario usuario = UsuarioFactory.get();
        Question question = QuestionFactory.get();
        Long questionId = question.getId();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchQuestionService.byId(questionId)).thenReturn(question);

        AnswerQuestionRequest request = new AnswerQuestionRequest();
        request.setAnswer(A);

        AnswerQuestionResponse response = tested.answer(questionId, request);

        verify(usuarioAutenticadoService).get();
        verify(searchQuestionService).byId(questionId);
        verify(usuarioRepository).save(usuario);

        assertTrue(response.isCorrect());
    }

    @Test
    @DisplayName("Deve retornar false quando a resposta estiver incorreta")
    void deveRetornarFalseQuandoRespostaEstiverIncorreta() {

        Usuario usuario = UsuarioFactory.get();
        Question question = QuestionFactory.get();
        Long questionId = question.getId();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchQuestionService.byId(questionId)).thenReturn(question);

        AnswerQuestionRequest request = new AnswerQuestionRequest();
        request.setAnswer(B);

        AnswerQuestionResponse response = tested.answer(questionId, request);

        verify(usuarioAutenticadoService).get();
        verify(searchQuestionService).byId(questionId);

        assertFalse(response.isCorrect());
    }

    @Test
    @DisplayName("Deve ganhar 60 XP quando responder corretamente em atividade incompleta")
    void deveGanhar60XpQuandoResponderCorretamenteAtividadeIncompleta() {

        Usuario usuario = UsuarioFactory.get();
        Question question = QuestionFactory.get();
        Long questionId = question.getId();

        final int XP_EARNED_PER_CORRECT_ANSWER = 60;
        Long xpExpected = usuario.getXp() + XP_EARNED_PER_CORRECT_ANSWER;

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchQuestionService.byId(questionId)).thenReturn(question);

        AnswerQuestionRequest request = new AnswerQuestionRequest();
        request.setAnswer(A);

        tested.answer(questionId, request);

        verify(usuarioAutenticadoService).get();
        verify(searchQuestionService).byId(questionId);
        verify(usuarioRepository).save(usuario);

        assertEquals(xpExpected, usuario.getXp());
    }

    @Test
    @DisplayName("Deve ganhar 10 XP quando responder corretamente em atividade completa")
    void deveGanhar10XpQuandoResponderCorretamenteAtividadeCompleta() {

        Usuario usuario = UsuarioFactory.get();
        Question question = QuestionFactory.get();
        Long questionId = question.getId();
        question.setActivity(ActivityFactory.get());
        usuario.setFinishedActivities(Collections.singletonList(question.getActivity()));

        final int XP_EARNED_PER_CORRECT_ANSWER_PRACTICED = 10;
        Long xpExpected = usuario.getXp() + XP_EARNED_PER_CORRECT_ANSWER_PRACTICED;

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchQuestionService.byId(questionId)).thenReturn(question);

        AnswerQuestionRequest request = new AnswerQuestionRequest();
        request.setAnswer(A);

        tested.answer(questionId, request);

        verify(usuarioAutenticadoService).get();
        verify(searchQuestionService).byId(questionId);
        verify(usuarioRepository).save(usuario);

        assertEquals(xpExpected, usuario.getXp());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        when(usuarioAutenticadoService.get()).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"));

        AnswerQuestionRequest request = new AnswerQuestionRequest();
        request.setAnswer(A);

        assertThrows(ResponseStatusException.class, () -> tested.answer(getRandomLong(), request));

        verify(usuarioAutenticadoService).get();
    }

    @Test
    @DisplayName("Deve lançar exceção quando questão não for encontrada")
    void deveLancarExcecaoQuandoQuestaoNaoForEncontrada() {

        Usuario usuario = UsuarioFactory.get();
        Question question = QuestionFactory.get();
        Long questionId = question.getId();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchQuestionService.byId(questionId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Question not found for this ID"));

        AnswerQuestionRequest request = new AnswerQuestionRequest();
        request.setAnswer(A);

        assertThrows(ResponseStatusException.class, () -> tested.answer(questionId, request));

        verify(usuarioAutenticadoService).get();
        verify(searchQuestionService).byId(questionId);
    }
}

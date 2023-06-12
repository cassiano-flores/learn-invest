package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.mapper.ListActivityQuestionsMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ListActivityQuestionsServiceTest {

    @InjectMocks
    private ListActivityQuestionsService tested;

    @Mock
    private SearchActivityService searchActivityService;

    @Test
    @DisplayName("Deve listar todas questões da atividade com sucesso")
    void deveListarQuestoesDaAtividadeComSucesso() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);

        List<ListActivityQuestionsResponse> questions = tested.listAllQuestions(activityId);

        verify(searchActivityService).byId(activityId);

        List<ListActivityQuestionsResponse> expectedQuestions = activity.getQuestions().stream()
                .map(ListActivityQuestionsMapper::toResponse)
                .collect(toList());

        assertEquals(expectedQuestions.size(), questions.size());

        for (int i = 0; i < expectedQuestions.size(); i++) {
            assertEquals(expectedQuestions.get(i).getId(), questions.get(i).getId());
        }
    }

    @Test
    @DisplayName("Deve listar cinco questões aleatórias da atividade com sucesso")
    void deveListarCincoQuestoesDaAtividadeComSucesso() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);

        List<ListActivityQuestionsResponse> questions = tested.listFiveQuestions(activityId);

        verify(searchActivityService).byId(activityId);

        List<ListActivityQuestionsResponse> allQuestions = activity.getQuestions().stream()
                .map(ListActivityQuestionsMapper::toResponse)
                .collect(Collectors.toList());

        for (ListActivityQuestionsResponse question : questions) {
            boolean questionExists = allQuestions.stream()
                    .anyMatch(q -> q.getId().equals(question.getId()));
            assertTrue(questionExists);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando atividade não for encontrada em listAllQuestions")
    void deveLancarExcecaoQuandoAtividadeNaoForEncontradaEmListAllQuestions() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(searchActivityService.byId(activityId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Activity not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.listAllQuestions(activityId));

        verify(searchActivityService).byId(activityId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando atividade não for encontrada em listFiveQuestions")
    void deveLancarExcecaoQuandoAtividadeNaoForEncontradaEmListFiveQuestions() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(searchActivityService.byId(activityId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Activity not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.listFiveQuestions(activityId));

        verify(searchActivityService).byId(activityId);
    }
}

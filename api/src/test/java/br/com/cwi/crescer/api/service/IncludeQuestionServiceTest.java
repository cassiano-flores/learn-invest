package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.IncludeQuestionRequest;
import br.com.cwi.crescer.api.controller.response.QuestionResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.mapper.QuestionMapper;
import br.com.cwi.crescer.api.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.domain.enums.AnswerOptions.A;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.mapper.QuestionMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class IncludeQuestionServiceTest {

    @InjectMocks
    private IncludeQuestionService tested;

    @Mock
    private SearchActivityService searchActivityService;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("Deve criar nova Question com sucesso")
    void deveCriarNovaQuestionComSucesso() {

        IncludeQuestionRequest request = IncludeQuestionRequest.builder()
                .activityId(getRandomLong())
                .questionText("Test")
                .alternativeA("Test")
                .alternativeB("Test")
                .alternativeC("Test")
                .alternativeD("Test")
                .answer(A)
                .build();

        Activity activity = ActivityFactory.get();
        Question question = QuestionMapper.toEntity(request);

        when(searchActivityService.byId(request.getActivityId())).thenReturn(activity);

        QuestionResponse response = tested.include(request);

        verify(searchActivityService).byId(request.getActivityId());
        verify(questionRepository).save(question);

        assertEquals(toResponse(question).getId(), response.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar atividade")
    void deveLancarExcecaoQuandoAtividadeNaoEncontrada() {

        IncludeQuestionRequest request = IncludeQuestionRequest.builder()
                .activityId(getRandomLong())
                .questionText("Test")
                .alternativeA("Test")
                .alternativeB("Test")
                .alternativeC("Test")
                .alternativeD("Test")
                .answer(A)
                .build();

        doThrow(new ResponseStatusException(NOT_FOUND, "Activity not found with this ID"))
                .when(searchActivityService)
                .byId(request.getActivityId());

        assertThrows(ResponseStatusException.class, () -> tested.include(request));

        verify(searchActivityService).byId(request.getActivityId());
    }
}

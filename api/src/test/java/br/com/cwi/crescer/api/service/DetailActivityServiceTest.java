package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ActivityAvailableResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class DetailActivityServiceTest {

    @InjectMocks
    private DetailActivityService tested;

    @Mock
    private SearchActivityService searchActivityService;

    @Test
    @DisplayName("Deve detalhar uma atividade com sucesso")
    void deveDetalharUmaAtividadeComSucesso() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(searchActivityService.byId(activityId)).thenReturn(activity);

        ActivityAvailableResponse response = tested.detail(activityId);

        verify(searchActivityService).byId(activityId);

        assertEquals(response.getId(), activity.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando atividade não for encontrada")
    void deveLancarExcecaoQuandoAtividadeNaoForEncontrada() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(searchActivityService.byId(activityId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Activity not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.detail(activityId));

        verify(searchActivityService).byId(activityId);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.repository.ActivityRepository;
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
class SearchActivityServiceTest {

    @InjectMocks
    private SearchActivityService tested;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    @DisplayName("Deve retornar atividade com sucesso")
    void deveRetornarAtividadeComSucesso() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

        Activity activityExpected = tested.byId(activityId);

        verify(activityRepository).findById(activityId);

        assertEquals(activityExpected, activity);
    }

    @Test
    @DisplayName("Deve lançar exceção quando atividade não for encontrada")
    void deveLancarExcecaoQuandoAtividadeNaoForEncontrada() {

        Activity activity = ActivityFactory.get();
        Long activityId = activity.getId();

        when(activityRepository.findById(activityId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Activity not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.byId(activityId));

        verify(activityRepository).findById(activityId);
    }
}

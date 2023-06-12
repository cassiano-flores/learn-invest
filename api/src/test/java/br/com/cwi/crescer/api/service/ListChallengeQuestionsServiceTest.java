package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ListChallengeQuestionsServiceTest {

    @InjectMocks
    private ListChallengeQuestionsService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve listar cinco questões para Challenge com sucesso")
    void deveListarCincoQuestoesParaChallengeComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        usuario.setFinishedActivities(Arrays.asList(ActivityFactory.get(), ActivityFactory.get(), ActivityFactory.get()));

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<ListActivityQuestionsResponse> result = tested.listFiveQuestions();

        verify(usuarioAutenticadoService).get();

        assertEquals(5, result.size());

        List<Activity> finishedActivities = usuario.getFinishedActivities();
        for (ListActivityQuestionsResponse response : result) {
            boolean found = false;
            for (Activity activity : finishedActivities) {
                if (activity.getQuestions().stream().anyMatch(q -> q.getId().equals(response.getId()))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário logado não encontrado")
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEncontrado() {

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found"))
                .when(usuarioAutenticadoService)
                .get();

        assertThrows(ResponseStatusException.class, () -> tested.listFiveQuestions());

        verify(usuarioAutenticadoService).get();
    }

    @Test
    @DisplayName("Deve listar cinco questões para Challenge com sucesso quando usuário não tiver atividades finalizadas")
    void deveListarCincoQuestoesParaChallengeComSucessoQuandoUsuarioNaoTiverAtividadesFinalizadas() {

        Usuario usuario = UsuarioFactory.get();
        usuario.setFinishedActivities(new ArrayList<>());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<ListActivityQuestionsResponse> result = tested.listFiveQuestions();

        verify(usuarioAutenticadoService).get();

        assertEquals(5, result.size());

        List<Activity> activities = usuario.getActivities();
        for (ListActivityQuestionsResponse response : result) {
            boolean found = false;
            for (Activity activity : activities) {
                if (activity.getQuestions().stream().anyMatch(q -> q.getId().equals(response.getId()))) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }
}

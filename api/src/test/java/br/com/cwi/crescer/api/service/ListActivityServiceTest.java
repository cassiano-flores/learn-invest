package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ActivityResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.factories.ActivityFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.ListActivityMapper;
import br.com.cwi.crescer.api.repository.ActivityRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class ListActivityServiceTest {

    @InjectMocks
    private ListActivityService tested;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Test
    @DisplayName("Deve listar todas atividades com sucesso")
    void deveListarTodasAtividadesComSucesso() {

        Activity activity1 = ActivityFactory.get();
        Activity activity2 = ActivityFactory.get();
        List<Activity> activities = Arrays.asList(activity1, activity2);

        when(activityRepository.findAllByOrderById()).thenReturn(activities);

        List<ActivityResponse> responses = tested.list();

        verify(activityRepository).findAllByOrderById();

        List<ActivityResponse> expectedResponses = activities.stream()
                .map(ListActivityMapper::toResponse)
                .collect(Collectors.toList());

        assertEquals(expectedResponses.size(), responses.size());

        for (int i = 0; i < expectedResponses.size(); i++) {
            assertEquals(expectedResponses.get(i).getId(), responses.get(i).getId());
        }
    }

    @Test
    @DisplayName("Deve listar todas atividades disponíveis com sucesso")
    void deveListarTodasAtividadesDisponiveisComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(searchUsuarioService.byId(usuarioId)).thenReturn(usuario);

        List<ActivityResponse> activities = tested.listAvailable();

        verify(searchUsuarioService).byId(usuarioId);

        List<ActivityResponse> expectedResponses = usuario.getActivities().stream()
                .map(ListActivityMapper::toResponse)
                .collect(Collectors.toList());

        for (ActivityResponse activity : activities) {
            boolean activityExists = expectedResponses.stream()
                    .anyMatch(q -> q.getId().equals(activity.getId()));
            assertTrue(activityExists);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(searchUsuarioService.byId(usuarioId)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.listAvailable());

        verify(searchUsuarioService).byId(usuarioId);
    }
}

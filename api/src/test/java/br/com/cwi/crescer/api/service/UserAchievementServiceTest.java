package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.UserAchievementResponse;
import br.com.cwi.crescer.api.domain.Achievement;
import br.com.cwi.crescer.api.factories.UserAchievementFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAchievementServiceTest {

    @InjectMocks
    private UserAchievementService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve retornar uma lista vazia se o usuario não possui nenhum Achievement")
    void DeveRetornarUmaListaVaziaSeOUsuarioNaoPossuiNenhumAchievement(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<UserAchievementResponse> achievement = tested.get();

        assertEquals(achievement.size(), usuario.getAchievements().size());
    }

    @Test
    @DisplayName("Deve retornar uma lista de achievement")
    void DeveRetornarUmaListaDeAchievement(){
        Usuario usuario = UsuarioFactory.get();
        usuario.getAchievements().add(UserAchievementFactory.get());
        usuario.getAchievements().add(UserAchievementFactory.get());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<UserAchievementResponse> achievement = tested.get();

        assertEquals(achievement.size(), usuario.getAchievements().size());
    }
}

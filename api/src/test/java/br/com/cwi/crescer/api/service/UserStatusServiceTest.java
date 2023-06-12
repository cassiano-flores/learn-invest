package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.UserStatusResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserStatusServiceTest {
    @InjectMocks
    private UserStatusService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve retornar o status do usuario logado corretamente")
    void DeveRetornarOStatusDoUsuarioLogadoCorretamente() {
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        UserStatusResponse status = tested.get();

        assertEquals(status.getLeagueTitle(), usuario.getLeagueTitle());
        assertEquals(status.getLeaguePoints(), usuario.getLeaguePoints());
        assertEquals(status.getXp(), usuario.getXp());
        assertEquals(status.getCoins(), usuario.getCoins());
    }
}
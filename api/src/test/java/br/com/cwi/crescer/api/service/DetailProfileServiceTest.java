package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ProfileResponse;
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
public class DetailProfileServiceTest {

    @InjectMocks
    private DetailProfileService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve detalhar o perfil do usuario logado")
    void DeveDetalharOPerfilDoUsuarioLogado(){
        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        ProfileResponse userProfile = tested.detail();

        assertEquals(userProfile.getName(), usuario.getName());
        assertEquals(userProfile.getNickname(), usuario.getNickname());
        assertEquals(userProfile.getEmail(), usuario.getEmail());
        assertEquals(userProfile.getUpdateIn(), usuario.getUpdateIn());
        assertEquals(userProfile.getCreateIn(), usuario.getCreateIn());
        assertEquals(userProfile.getCurrentIcon().getName(), usuario.getCurrentIcon().getName());
    }
}

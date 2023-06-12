package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ProfileResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DetailProfileMapperTest {

    @Test
    @DisplayName("Deve mapear corretamente um usuario para usuario response")
    void deveMapearUmUsuarioParaProfileResponse(){
        Usuario usuario = UsuarioFactory.get();

        ProfileResponse response = DetailProfileMapper.toResponse(usuario);

        assertEquals(response.getName(), usuario.getName());
        assertEquals(response.getNickname(), usuario.getNickname());
        assertEquals(response.getEmail(), usuario.getEmail());
        assertEquals(response.getCurrentIcon().getId(), usuario.getCurrentIcon().getId());
        assertEquals(response.getCurrentIcon().getName(), usuario.getCurrentIcon().getName());
        assertEquals(response.getCurrentIcon().getPrice(), usuario.getCurrentIcon().getPrice());
        assertEquals(response.getCreateIn(), usuario.getCreateIn());
        assertEquals(response.getUpdateIn(), usuario.getUpdateIn());
    }
}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.ProfileEditedResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProfileEditMapperTest {

    @Test
    @DisplayName("Deve mapear um usuario para profileEditedResponse")
    void deveMapearUsuarioParaProfileEditedResponse() {

        Usuario usuario = UsuarioFactory.get();

        ProfileEditedResponse response = ProfileEditMapper.toResponse(usuario);

        assertEquals(usuario.getName(), response.getName());
        assertEquals(usuario.getNickname(), response.getNickname());
        assertEquals(usuario.getCurrentIcon().getId(), response.getCurrentIcon().getId());
    }
}

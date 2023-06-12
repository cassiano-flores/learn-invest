package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UsuarioChallengeHistoryResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioChallengeHistoryMapperTest {

    @Test
    @DisplayName("Deve mapear um Usuario para UsuarioChallengeHistoryResponse")
    void deveMapearUsuarioParaUsuarioChallengeHistoryResponse() {

        Usuario usuario = UsuarioFactory.get();

        UsuarioChallengeHistoryResponse response = UsuarioChallengeHistoryMapper.toResponse(usuario);

        assertEquals(response.getId(), usuario.getId());
        assertEquals(response.getName(), usuario.getName());
        assertEquals(response.getNickname(), usuario.getNickname());
        assertEquals(response.getCurrentIconId(), usuario.getCurrentIcon().getId());
    }
}

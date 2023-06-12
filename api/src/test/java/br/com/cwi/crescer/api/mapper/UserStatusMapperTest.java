package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UserStatusResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserStatusMapperTest {

    @Test
    @DisplayName("Deve mapear corretamente os status do usuario para UserStatusResponse")
    void DeveMapearCorretamenteOsStatusDoUsuarioParaUserStatusResponse(){
        Usuario usuario = UsuarioFactory.get();

        UserStatusResponse response = UserStatusMapper.toResponse(usuario);

        assertEquals(response.getXp(), usuario.getXp());
        assertEquals(response.getCoins(), usuario.getCoins());
        assertEquals(response.getLeaguePoints(), usuario.getLeaguePoints());
        assertEquals(response.getLeagueTitle(), usuario.getLeagueTitle());

    }
}

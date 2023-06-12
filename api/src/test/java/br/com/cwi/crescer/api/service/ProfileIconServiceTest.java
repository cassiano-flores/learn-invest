package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.mapper.IconMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ProfileIconServiceTest {

    @InjectMocks
    private ProfileIconService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve listar ícones do perfil com sucesso")
    void deveListarIconesPerfilComSucesso() {

        Usuario usuario = UsuarioFactory.get();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);

        List<IconResponse> expectedIcons = usuario.getIcons().stream()
                .map(IconMapper::toResponse)
                .collect(toList());

        List<IconResponse> iconsResponse = tested.list();

        verify(usuarioAutenticadoService).get();

        for (IconResponse icon : iconsResponse) {
            boolean iconExists = expectedIcons.stream()
                    .anyMatch(i -> i.getId().equals(icon.getId()));
            assertTrue(iconExists);
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {

        when(usuarioAutenticadoService.get()).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found with this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.list());

        verify(usuarioAutenticadoService).get();
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ProfileEditedRequest;
import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.factories.IconFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
public class ProfileEditServiceTest {

    @InjectMocks
    private ProfileEditService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchIconService searchIconService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve editar corretamente name, nickname, icone")
    void deveEditarCorretamente() {

        Usuario usuario = UsuarioFactory.get();
        Icon icon = IconFactory.get();
        Long iconId = icon.getId();
        usuario.getIcons().add(icon);

        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname("Nick editado")
                .iconId(iconId)
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(iconId)).thenReturn(icon);

        tested.edit(request);

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(iconId);
        verify(usuarioRepository).save(usuario);

        assertEquals(usuario.getName(), request.getName());
        assertEquals(usuario.getNickname(), request.getNickname());
        assertEquals(usuario.getCurrentIcon().getId(), request.getIconId());
    }

    @Test
    @DisplayName("Deve editar corretamente quando nickname for nulo")
    void deveEditarQuandoNicknameForNull() {

        Usuario usuario = UsuarioFactory.get();
        Icon icon = IconFactory.get();
        Long iconId = icon.getId();
        usuario.getIcons().add(icon);

        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname(null)
                .iconId(iconId)
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(icon.getId())).thenReturn(icon);

        tested.edit(request);

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(icon.getId());
        verify(usuarioRepository).save(usuario);

        assertEquals(usuario.getName(), request.getName());
        assertNull(usuario.getNickname());
        assertEquals(usuario.getCurrentIcon().getId(), request.getIconId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar setar um icone que não pertence ao usuário")
    void deveLancarExcecaoQuandoNaoPossuirIcone() {

        Usuario usuario = UsuarioFactory.get();
        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname("Nick editado")
                .iconId(2L)
                .build();

        Long iconIdRequest = request.getIconId();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(iconIdRequest)).thenThrow(new ResponseStatusException(BAD_REQUEST, "User don't own required icon"));

        assertThrows(ResponseStatusException.class, () -> tested.edit(request));

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(iconIdRequest);
    }

    @Test
    @DisplayName("Não deve lançar exceção ao editar corretamente")
    void naoDeveLancarExcecaoAoEditar() {

        Usuario usuario = UsuarioFactory.get();
        Icon icon = IconFactory.get();
        Long iconId = icon.getId();
        usuario.getIcons().add(icon);

        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname("Nick editado")
                .iconId(iconId)
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(iconId)).thenReturn(icon);

        assertDoesNotThrow(() -> tested.edit(request));

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(iconId);
        verify(usuarioRepository).save(usuario);

        assertEquals(usuario.getName(), request.getName());
        assertEquals(usuario.getNickname(), request.getNickname());
        assertEquals(usuario.getCurrentIcon().getId(), request.getIconId());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando usuário possuir o ícone necessário")
    void naoDeveLancarExcecaoQuandoUsuarioPossuirIconeNecessario() {
        Usuario usuario = UsuarioFactory.get();
        Icon icon = IconFactory.get();
        Long iconId = icon.getId();
        usuario.getIcons().add(icon);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(iconId)).thenReturn(icon);

        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname("Nick editado")
                .iconId(iconId)
                .build();

        assertDoesNotThrow(() -> tested.edit(request));

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(iconId);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não possuir o ícone necessário")
    void deveLancarExcecaoQuandoUsuarioNaoPossuirIconeNecessario() {
        Usuario usuario = UsuarioFactory.get();
        Icon icon = IconFactory.get();
        usuario.getIcons().add(icon);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(2L)).thenThrow(new ResponseStatusException(BAD_REQUEST, "User don't own required icon"));

        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname("Nick editado")
                .iconId(2L)
                .build();

        assertThrows(ResponseStatusException.class, () -> tested.edit(request));

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(2L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário possui ícones, mas nenhum é o necessário")
    void deveLancarExcecaoQuandoUsuarioNaoPossuiIconeNecessarioEntreOsSeusIcons() {
        Usuario usuario = UsuarioFactory.get();
        Icon icon = IconFactory.get();
        usuario.getIcons().add(icon);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchIconService.byId(2L)).thenReturn(IconFactory.get());

        ProfileEditedRequest request = ProfileEditedRequest.builder()
                .name("Nome editado")
                .nickname("Nick editado")
                .iconId(2L)
                .build();

        assertThrows(ResponseStatusException.class, () -> tested.edit(request));

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(2L);
    }
}

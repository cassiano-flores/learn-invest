package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.controller.response.ProfileSummaryResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.FriendshipFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class SearchUsuarioServiceTest {

    @InjectMocks
    private SearchUsuarioService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar usuário com sucesso pelo ID")
    void deveRetornarUsuarioComSucessoPeloId() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));

        Usuario usuarioExpected = tested.byId(usuarioId);

        verify(usuarioRepository).findById(usuarioId);

        assertEquals(usuarioExpected, usuario);
    }

    @Test
    @DisplayName("Deve retornar usuário com sucesso pelo email")
    void deveRetornarUsuarioComSucessoPeloEmail() {

        Usuario usuario = UsuarioFactory.get();
        String email = usuario.getEmail();

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Usuario usuarioExpected = tested.byEmail(email);

        verify(usuarioRepository).findByEmail(email);

        assertEquals(usuarioExpected, usuario);
    }

    @Test
    @DisplayName("Deve retornar usuário com sucesso pelo token")
    void deveRetornarUsuarioComSucessoPeloToken() {

        Usuario usuario = UsuarioFactory.get();
        String token = usuario.getToken();

        when(usuarioRepository.findByToken(token)).thenReturn(Optional.of(usuario));

        Usuario usuarioExpected = tested.byToken(token);

        verify(usuarioRepository).findByToken(token);

        assertEquals(usuarioExpected, usuario);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado pelo ID")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontradoPeloId() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        when(usuarioRepository.findById(usuarioId)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.byId(usuarioId));

        verify(usuarioRepository).findById(usuarioId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado pelo email")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontradoPeloEmail() {

        Usuario usuario = UsuarioFactory.get();
        String email = usuario.getEmail();

        when(usuarioRepository.findByEmail(email)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this email"));

        assertThrows(ResponseStatusException.class, () -> tested.byEmail(email));

        verify(usuarioRepository).findByEmail(email);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado pelo token")
    void deveLancarExcecaoQuandoUsuarioNaoForEncontradoPeloToken() {

        Usuario usuario = UsuarioFactory.get();
        String token = usuario.getToken();

        when(usuarioRepository.findByToken(token)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this token"));

        assertThrows(ResponseStatusException.class, () -> tested.byToken(token));

        verify(usuarioRepository).findByToken(token);
    }

    @Test
    @DisplayName("Deve retornar todos os usuarios de forma paginada quando não for informado nenhum texto")
    void deveRetornarTodosUsuariosQuandoNaoForInformadoNenhumTexto(){

        List<Usuario> resultadoDaPesquisaList = new ArrayList<>();
        Usuario usuario = UsuarioFactory.get();
        usuario.setName("Maria");
        resultadoDaPesquisaList.add(usuario);
        resultadoDaPesquisaList.add(UsuarioFactory.get());
        resultadoDaPesquisaList.add(UsuarioFactory.get());

        Pageable pageable = PageRequest.of(0,5);

        Page<Usuario> resultadoDaPesquisaPage = new PageImpl<Usuario>(resultadoDaPesquisaList);

        when(usuarioRepository.findByNameContainsIgnoreCaseOrEmailContainsIgnoreCase("","",pageable)).thenReturn(resultadoDaPesquisaPage);
        Page<ProfileSummaryResponse> response = tested.byNameOrEmailContains("", pageable);

        assertEquals(response.getTotalElements(), resultadoDaPesquisaPage.getTotalElements());

    }

}

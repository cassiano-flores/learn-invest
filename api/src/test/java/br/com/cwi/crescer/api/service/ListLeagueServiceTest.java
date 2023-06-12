package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.FriendshipFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ListLeagueServiceTest {

    @InjectMocks
    private ListLeagueService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve retornar amigos do usuário logado em ordem de pontos")
    void deveRetornarAmigosDoUsuarioLogadoEmOrdemDePontos() {

        Usuario usuario = UsuarioFactory.get();
        usuario.setLeaguePoints(30L);
        Long usuarioId = usuario.getId();

        Usuario usuario1 = UsuarioFactory.get();
        Usuario usuario2 = UsuarioFactory.get();
        usuario1.setLeaguePoints(10L);
        usuario2.setLeaguePoints(20L);

        Friendship friendship1 = FriendshipFactory.get();
        friendship1.setUsuarioId(usuarioId);
        friendship1.setFriend(usuario1);
        Friendship friendship2 = FriendshipFactory.get();
        friendship2.setUsuarioId(usuarioId);
        friendship2.setFriend(usuario2);

        List<Long> ids = Arrays.asList(usuario1.getId(), usuario2.getId(), usuario.getId());
        List<Usuario> usuarios = Arrays.asList(usuario, usuario2, usuario1);

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(friendshipRepository.findAllFriendsIdsByUsuarioId(usuario.getId()))
                .thenReturn(Arrays.asList(friendship1, friendship2));
        when(usuarioRepository.findByIdInOrderByLeaguePointsDesc
                (ids, Pageable.unpaged()))
                .thenReturn(new PageImpl<>(usuarios));

        Page<FriendshipResponse> response = tested.list(Pageable.unpaged());

        verify(usuarioAutenticadoService).getId();
        verify(friendshipRepository).findAllFriendsIdsByUsuarioId(usuarioId);
        verify(usuarioRepository).findByIdInOrderByLeaguePointsDesc(ids, Pageable.unpaged());

        assertAll(
                () -> assertEquals(usuario.getId(), response.getContent().get(0).getId()),
                () -> assertEquals(usuario2.getId(), response.getContent().get(1).getId()),
                () -> assertEquals(usuario1.getId(), response.getContent().get(2).getId())
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário logado não encontrado")
    void deveLancarExcecaoQuandoUsuarioUsuarioLogadoNaoEncontrado() {

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found"))
                .when(usuarioAutenticadoService)
                .getId();

        assertThrows(ResponseStatusException.class, () -> tested.list(Pageable.unpaged()));

        verify(usuarioAutenticadoService).getId();
    }
}

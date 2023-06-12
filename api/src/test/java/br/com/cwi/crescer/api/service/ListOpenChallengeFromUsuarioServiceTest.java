package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.domain.UsuarioChallenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import br.com.cwi.crescer.api.factories.UsuarioChallengeFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ListOpenChallengeFromUsuarioServiceTest {

    @InjectMocks
    private ListOpenChallengeFromUsuarioService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ChallengeRepository challengeRepository;

    @Test
    @DisplayName("Deve listar todos Challenge enviados em aberto do usuário")
    void deveListarTodosChallengeEnviadosEmAbertoDoUsuario() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        UsuarioChallenge usuarioChallenge1 = UsuarioChallengeFactory.get();
        usuarioChallenge1.setUsuarioSender(usuario);

        UsuarioChallenge usuarioChallenge2 = UsuarioChallengeFactory.get();
        usuarioChallenge1.setUsuarioSender(usuario);

        Challenge challenge1 = ChallengeFactory.get();
        challenge1.setUsuarioChallenge(usuarioChallenge1);

        Challenge challenge2 = ChallengeFactory.get();
        challenge2.setUsuarioChallenge(usuarioChallenge2);

        List<Challenge> challenges = Arrays.asList(challenge1, challenge2);

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(challengeRepository.findAllByUsuarioChallengeUsuarioSenderIdAndIsCompleteIsFalse(usuarioId))
                .thenReturn(challenges);

        List<ListChallengeResponse> response = tested.listSent();

        List<Long> responseIds = response.stream()
                .map(ListChallengeResponse::getId)
                .collect(Collectors.toList());

        verify(usuarioAutenticadoService).getId();
        verify(challengeRepository)
                .findAllByUsuarioChallengeUsuarioSenderIdAndIsCompleteIsFalse(usuarioId);

        assertEquals(challenges.size(), response.size());

        for (ListChallengeResponse challenge : response) {
            assertTrue(responseIds.contains(challenge.getId()));
        }
    }

    @Test
    @DisplayName("Deve listar todos Challenge recebidos em aberto do usuário")
    void deveListarTodosChallengeRecebidosEmAbertoDoUsuario() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        UsuarioChallenge usuarioChallenge1 = UsuarioChallengeFactory.get();
        usuarioChallenge1.setUsuarioSender(usuario);

        UsuarioChallenge usuarioChallenge2 = UsuarioChallengeFactory.get();
        usuarioChallenge1.setUsuarioSender(usuario);

        Challenge challenge1 = ChallengeFactory.get();
        challenge1.setUsuarioChallenge(usuarioChallenge1);

        Challenge challenge2 = ChallengeFactory.get();
        challenge2.setUsuarioChallenge(usuarioChallenge2);

        List<Challenge> challenges = Arrays.asList(challenge1, challenge2);

        when(usuarioAutenticadoService.getId()).thenReturn(usuarioId);
        when(challengeRepository.findByUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse(usuarioId))
                .thenReturn(challenges);

        List<ListChallengeResponse> response = tested.listReceived();

        List<Long> responseIds = response.stream()
                .map(ListChallengeResponse::getId)
                .collect(Collectors.toList());

        verify(usuarioAutenticadoService).getId();
        verify(challengeRepository)
                .findByUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse(usuarioId);

        assertEquals(challenges.size(), response.size());

        for (ListChallengeResponse challenge : response) {
            assertTrue(responseIds.contains(challenge.getId()));
        }
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário sender não encontrado")
    void deveLancarExcecaoQuandoUsuarioSenderNaoEncontrado() {

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found"))
                .when(usuarioAutenticadoService)
                .getId();

        assertThrows(ResponseStatusException.class, () -> tested.listSent());

        verify(usuarioAutenticadoService).getId();
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário receiver não encontrado")
    void deveLancarExcecaoQuandoUsuarioReceiverNaoEncontrado() {

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found"))
                .when(usuarioAutenticadoService)
                .getId();

        assertThrows(ResponseStatusException.class, () -> tested.listReceived());

        verify(usuarioAutenticadoService).getId();
    }
}

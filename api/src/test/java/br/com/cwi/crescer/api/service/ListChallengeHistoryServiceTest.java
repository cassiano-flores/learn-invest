package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListChallengeHistoryResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.domain.UsuarioChallenge;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ListChallengeHistoryServiceTest {

    @InjectMocks
    private ListChallengeHistoryService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private ChallengeRepository challengeRepository;

    @Test
    @DisplayName("Deve listar todos Challenges concluídos com sucesso")
    void deveListarTodosChallengesConcluidosComSucesso() {

        Usuario usuario = UsuarioFactory.get();

        UsuarioChallenge usuarioChallenge1 = UsuarioChallenge.builder()
                .usuarioSender(usuario)
                .usuarioReceiver(UsuarioFactory.get())
                .build();

        UsuarioChallenge usuarioChallenge2 = UsuarioChallenge.builder()
                .usuarioSender(UsuarioFactory.get())
                .usuarioReceiver(usuario)
                .build();

        Challenge challenge1 = Challenge.builder()
                .id(getRandomLong())
                .isComplete(true)
                .usuarioChallenge(usuarioChallenge1)
                .build();

        Challenge challenge2 = Challenge.builder()
                .id(getRandomLong())
                .isComplete(true)
                .usuarioChallenge(usuarioChallenge2)
                .build();

        List<Challenge> challenges = Arrays.asList(challenge1, challenge2);
        PageImpl<Challenge> challengesPage = new PageImpl<>(challenges);

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(challengeRepository.findAllByUsuarioChallengeUsuarioSenderIdOrUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsTrue(
                usuario.getId(), usuario.getId(), Pageable.unpaged())).thenReturn(challengesPage);

        List<ListChallengeHistoryResponse> responseList = tested.list(Pageable.unpaged()).getContent();

        verify(usuarioAutenticadoService).get();
        verify(challengeRepository)
                .findAllByUsuarioChallengeUsuarioSenderIdOrUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsTrue
                        (usuario.getId(), usuario.getId(), Pageable.unpaged());

        assertEquals(2, responseList.size());
        assertEquals(challenge1.getUsuarioChallenge().getUsuarioSender().getId(), responseList.get(0).getCurrentUsuario().getId());
        assertEquals(challenge1.getUsuarioChallenge().getUsuarioSender().getId(), responseList.get(1).getOpponentUsuario().getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário logado não encontrado")
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEncontrado() {

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found"))
                .when(usuarioAutenticadoService)
                        .get();

        assertThrows(ResponseStatusException.class, () -> tested.list(Pageable.unpaged()));

        verify(usuarioAutenticadoService).get();
    }
}

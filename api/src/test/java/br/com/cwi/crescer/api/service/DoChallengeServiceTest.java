package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ResultActivityRequest;
import br.com.cwi.crescer.api.controller.response.ReplyChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.domain.UsuarioChallenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.repository.UsuarioChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.validations.ValidateExistingChallengeRequestService;
import br.com.cwi.crescer.api.service.validations.ValidateSameUsarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class DoChallengeServiceTest {

    @InjectMocks
    private DoChallengeService tested;

    @Mock
    private ValidateExistingChallengeRequestService validateExistingChallengeRequestService;

    @Mock
    private ValidateSameUsarioService validateSameUsarioService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private SearchChallengeService searchChallengeService;

    @Mock
    private UsuarioChallengeRepository usuarioChallengeRepository;

    @Mock
    private ChallengeRepository challengeRepository;

    @Test
    @DisplayName("Deve enviar desafio com sucesso")
    void deveEnviarDesafioComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioChallenged = UsuarioFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioChallengedId = usuarioChallenged.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchUsuarioService.byId(usuarioChallengedId)).thenReturn(usuarioChallenged);

        tested.send(usuarioChallengedId, request);

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(usuarioChallengedId);
        verify(validateSameUsarioService).validateSend(usuarioId, usuarioChallengedId);
        verify(validateExistingChallengeRequestService).validate(usuarioId, usuarioChallengedId);
        verify(validateExistingChallengeRequestService).validate(usuarioChallengedId, usuarioId);
        verify(usuarioChallengeRepository, times(1)).save(any(UsuarioChallenge.class));
        verify(challengeRepository, times(1)).save(any(Challenge.class));
    }

    @Test
    @DisplayName("Deve responder desafio com sucesso")
    void deveResponderDesafioComSucesso() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioChallenged = UsuarioFactory.get();
        Challenge challenge = ChallengeFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioChallengedId = usuarioChallenged.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchChallengeService.bySenderAndReceiverId(usuarioChallengedId, usuarioId)).thenReturn(challenge);

        ReplyChallengeResponse response = tested.reply(usuarioChallengedId, request);

        verify(usuarioAutenticadoService).get();
        verify(searchChallengeService).bySenderAndReceiverId(usuarioChallengedId, usuarioId);
        verify(validateSameUsarioService).validateReply(usuarioId, usuarioChallengedId);
        verify(challengeRepository).save(challenge);

        assertEquals(request.getScore(), challenge.getUsuarioReceiverScore());
        assertEquals(response.getChallengeId(), challenge.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar usuário logado send")
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEncontradoSend() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found"));

        assertThrows(ResponseStatusException.class, () -> tested.send(usuarioId, request));

        verify(usuarioAutenticadoService).get();
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar usuário logado reply")
    void deveLancarExcecaoQuandoUsuarioLogadoNaoEncontradoReply() {

        Usuario usuario = UsuarioFactory.get();
        Long usuarioId = usuario.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found"));

        assertThrows(ResponseStatusException.class, () -> tested.reply(usuarioId, request));

        verify(usuarioAutenticadoService).get();
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar usuário send")
    void deveLancarExcecaoQuandoNaoEncontrarUsuarioSend() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioChallenged = UsuarioFactory.get();
        Long usuarioChallengedId = usuarioChallenged.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchUsuarioService.byId(usuarioChallengedId)).thenThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.send(usuarioChallengedId, request));

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(usuarioChallengedId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar desafio pelo usuarioId e usuarioChallengedId")
    void deveLancarExcecaoQuandoNaoEncontrarDesafio() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioChallenged = UsuarioFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioChallengedId = usuarioChallenged.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchChallengeService.bySenderAndReceiverId(usuarioChallengedId, usuarioId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Challenge not found with this users"));

        assertThrows(ResponseStatusException.class, () -> tested.reply(usuarioChallengedId, request));

        verify(usuarioAutenticadoService).get();
        verify(searchChallengeService).bySenderAndReceiverId(usuarioChallengedId, usuarioId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar enviar desafio para o mesmo usuário send")
    void deveLancarExcecaoAoEnviarDesafioParaMesmoUsuarioSend() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioChallenged = UsuarioFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioChallengedId = usuario.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchUsuarioService.byId(usuarioChallengedId)).thenReturn(usuarioChallenged);

        doThrow(new ResponseStatusException(BAD_REQUEST, "Can't send a challenge request to yourself"))
                .when(validateSameUsarioService)
                .validateSend(usuarioId, usuarioId);

        assertThrows(ResponseStatusException.class, () -> tested.send(usuarioChallengedId, request));

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(usuarioChallengedId);
        verify(validateSameUsarioService).validateSend(usuarioId, usuarioId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar enviar desafio para o mesmo usuário reply")
    void deveLancarExcecaoAoEnviarDesafioParaMesmoUsuarioReply() {

        Challenge challenge = ChallengeFactory.getSameUsuario();
        Usuario usuario = challenge.getUsuarioChallenge().getUsuarioSender();
        Usuario usuarioChallenged = challenge.getUsuarioChallenge().getUsuarioReceiver();
        Long usuarioId = usuario.getId();
        Long usuarioChallengedId = usuarioChallenged.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchChallengeService.bySenderAndReceiverId(usuarioChallengedId, usuarioId)).thenReturn(challenge);

        doThrow(new ResponseStatusException(BAD_REQUEST, "Can't send a challenge request to yourself"))
                .when(validateSameUsarioService)
                .validateReply(usuarioChallengedId, usuarioChallengedId);

        assertThrows(ResponseStatusException.class, () -> tested.reply(usuarioChallengedId, request));

        verify(usuarioAutenticadoService).get();
        verify(searchChallengeService).bySenderAndReceiverId(usuarioChallengedId, usuarioId);
        verify(validateSameUsarioService).validateReply(usuarioChallengedId, usuarioChallengedId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando já tiver enviado um desafio para o usuário")
    void deveLancarExcecaoQuandoJaTiverEnviadoRequest() {

        Usuario usuario = UsuarioFactory.get();
        Usuario usuarioChallenged = UsuarioFactory.get();
        Long usuarioId = usuario.getId();
        Long usuarioChallengedId = usuarioChallenged.getId();

        ResultActivityRequest request = ResultActivityRequest.builder()
                .score(getRandomLong())
                .build();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(searchUsuarioService.byId(usuarioChallengedId)).thenReturn(usuarioChallenged);
        doThrow(new ResponseStatusException(BAD_REQUEST, "There is already a challenge request pending from this sender to this receiver"))
                .when(validateExistingChallengeRequestService)
                .validate(usuarioId, usuarioChallengedId);

        assertThrows(ResponseStatusException.class, () -> tested.send(usuarioChallengedId, request));

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(usuarioChallengedId);
        verify(validateExistingChallengeRequestService).validate(usuarioId, usuarioChallengedId);
    }
}

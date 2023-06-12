package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.PositiveOrZero;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class ResultChallengeServiceTest {

    @InjectMocks
    private ResultChallengeService tested;

    @Mock
    private SearchChallengeService searchChallengeService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    public static final int WINNER_LEAGUE_POINTS = 10;
    public static final int LOSER_LEAGUE_POINTS = 5;
    public static final int WINNER_COINS = 50;
    public static final int DRAW_COINS = 10;

    @Test
    @DisplayName("Deve retornar resultado com sucesso quando sender win")
    void deveRetornarResultadoComSucessoQuandoSenderWin() {

        Challenge challenge = ChallengeFactory.get();
        challenge.setUsuarioSenderScore(5L);
        challenge.setUsuarioReceiverScore(0L);

        Usuario sender = challenge.getUsuarioChallenge().getUsuarioSender();
        Usuario receiver = challenge.getUsuarioChallenge().getUsuarioReceiver();
        Long challengeId = challenge.getId();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        Long expectedPointsSender = sender.getLeaguePoints() + WINNER_LEAGUE_POINTS;
        Long expectedCoinsSender = sender.getCoins() + WINNER_COINS;

        @PositiveOrZero
        Long expectedPointsReceiver = receiver.getLeaguePoints() - LOSER_LEAGUE_POINTS;

        when(searchChallengeService.byId(challengeId)).thenReturn(challenge);
        when(searchUsuarioService.byId(senderId)).thenReturn(sender);
        when(searchUsuarioService.byId(receiverId)).thenReturn(receiver);

        tested.result(challengeId);

        verify(searchChallengeService).byId(challengeId);
        verify(searchUsuarioService).byId(senderId);
        verify(searchUsuarioService).byId(receiverId);
        verify(challengeRepository).save(challenge);
        verify(usuarioRepository).save(sender);
        verify(usuarioRepository).save(receiver);

        assertEquals(expectedPointsSender, sender.getLeaguePoints());
        assertEquals(expectedCoinsSender, sender.getCoins());
        assertEquals(expectedPointsReceiver, receiver.getLeaguePoints());
        assertTrue(challenge.isComplete());
    }

    @Test
    @DisplayName("Deve retornar resultado com sucesso quando receiver win")
    void deveRetornarResultadoComSucessoQuandoReceiverWin() {

        Challenge challenge = ChallengeFactory.get();
        challenge.setUsuarioSenderScore(0L);
        challenge.setUsuarioReceiverScore(5L);

        Usuario sender = challenge.getUsuarioChallenge().getUsuarioSender();
        Usuario receiver = challenge.getUsuarioChallenge().getUsuarioReceiver();
        Long challengeId = challenge.getId();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        Long expectedPointsReceiver = receiver.getLeaguePoints() + WINNER_LEAGUE_POINTS;
        Long expectedCoinsReceiver = receiver.getCoins() + WINNER_COINS;

        @PositiveOrZero
        Long expectedPointsSender = sender.getLeaguePoints() - LOSER_LEAGUE_POINTS;

        when(searchChallengeService.byId(challengeId)).thenReturn(challenge);
        when(searchUsuarioService.byId(senderId)).thenReturn(sender);
        when(searchUsuarioService.byId(receiverId)).thenReturn(receiver);

        tested.result(challengeId);

        verify(searchChallengeService).byId(challengeId);
        verify(searchUsuarioService).byId(senderId);
        verify(searchUsuarioService).byId(receiverId);
        verify(challengeRepository).save(challenge);
        verify(usuarioRepository).save(sender);
        verify(usuarioRepository).save(receiver);

        assertEquals(expectedPointsReceiver, receiver.getLeaguePoints());
        assertEquals(expectedCoinsReceiver, receiver.getCoins());
        assertEquals(expectedPointsSender, sender.getLeaguePoints());
        assertTrue(challenge.isComplete());
    }

    @Test
    @DisplayName("Deve retornar resultado com sucesso quando empate")
    void deveRetornarResultadoComSucessoQuandoEmpate() {

        Challenge challenge = ChallengeFactory.get();
        challenge.setUsuarioSenderScore(5L);
        challenge.setUsuarioReceiverScore(5L);

        Usuario sender = challenge.getUsuarioChallenge().getUsuarioSender();
        Usuario receiver = challenge.getUsuarioChallenge().getUsuarioReceiver();
        Long challengeId = challenge.getId();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        Long expectedCoinsSender = sender.getCoins() + DRAW_COINS;
        Long expectedCoinsReceiver = receiver.getCoins() + DRAW_COINS;

        when(searchChallengeService.byId(challengeId)).thenReturn(challenge);
        when(searchUsuarioService.byId(senderId)).thenReturn(sender);
        when(searchUsuarioService.byId(receiverId)).thenReturn(receiver);

        tested.result(challengeId);

        verify(searchChallengeService).byId(challengeId);
        verify(searchUsuarioService).byId(senderId);
        verify(searchUsuarioService).byId(receiverId);
        verify(challengeRepository).save(challenge);
        verify(usuarioRepository).save(sender);
        verify(usuarioRepository).save(receiver);

        assertEquals(expectedCoinsSender, sender.getCoins());
        assertEquals(expectedCoinsReceiver, receiver.getCoins());
        assertTrue(challenge.isComplete());
    }

    @Test
    @DisplayName("Deve lançar exceção quando Challenge não encontrado")
    void deveLancarExcecaoQuandoChallengeNaoEncontrado() {

        Challenge challenge = ChallengeFactory.get();
        Long challengeId = challenge.getId();

        doThrow(new ResponseStatusException(NOT_FOUND, "Challenge not found for this ID"))
                .when(searchChallengeService)
                .byId(challengeId);

        assertThrows(ResponseStatusException.class, () -> tested.result(challengeId));

        verify(searchChallengeService).byId(challengeId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando Sender não encontrado")
    void deveLancarExcecaoQuandoSenderNaoEncontrado() {

        Challenge challenge = ChallengeFactory.get();
        Usuario sender = challenge.getUsuarioChallenge().getUsuarioSender();
        Long challengeId = challenge.getId();
        Long senderId = sender.getId();

        when(searchChallengeService.byId(challengeId)).thenReturn(challenge);

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"))
                .when(searchUsuarioService)
                .byId(senderId);

        assertThrows(ResponseStatusException.class, () -> tested.result(challengeId));

        verify(searchChallengeService).byId(challengeId);
        verify(searchUsuarioService).byId(senderId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando Receiver não encontrado")
    void deveLancarExcecaoQuandoReceiverNaoEncontrado() {

        Challenge challenge = ChallengeFactory.get();
        Usuario sender = challenge.getUsuarioChallenge().getUsuarioSender();
        Usuario receiver = challenge.getUsuarioChallenge().getUsuarioReceiver();
        Long challengeId = challenge.getId();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        when(searchChallengeService.byId(challengeId)).thenReturn(challenge);
        when(searchUsuarioService.byId(senderId)).thenReturn(sender);

        doThrow(new ResponseStatusException(NOT_FOUND, "User not found for this ID"))
                .when(searchUsuarioService)
                .byId(receiverId);

        assertThrows(ResponseStatusException.class, () -> tested.result(challengeId));

        verify(searchChallengeService).byId(challengeId);
        verify(searchUsuarioService).byId(senderId);
        verify(searchUsuarioService).byId(receiverId);
    }
}

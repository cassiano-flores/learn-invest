package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.factories.ChallengeFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
public class SearchChallengeServiceTest {

    @InjectMocks
    private SearchChallengeService tested;

    @Mock
    private ChallengeRepository challengeRepository;

    @Test
    @DisplayName("Deve retornar Challenge com sucesso pelo ID")
    void deveRetornarChallengeComSucessoPeloId(){

        Challenge challenge = ChallengeFactory.get();
        Long challengeId = challenge.getId();

        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));

        Challenge response = tested.byId(challengeId);

        verify(challengeRepository).findById(challengeId);

        assertEquals(response, challenge);
    }

    @Test
    @DisplayName("Deve retornar Challenge com sucesso pelo Sender e Receiver")
    void deveRetornarChallengeComSucessoPeloSenderReceiver(){

        Challenge challenge = ChallengeFactory.get();
        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        when(challengeRepository
                .findByUsuarioChallengeUsuarioSenderIdAndUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse
                        (senderId, receiverId)).thenReturn(Optional.of(challenge));

        Challenge response = tested.bySenderAndReceiverId(senderId, receiverId);

        verify(challengeRepository)
                .findByUsuarioChallengeUsuarioSenderIdAndUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse
                        (senderId, receiverId);

        assertEquals(response, challenge);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar Challenge pelo ID")
    void deveLancarExcecaoQuandoNaoEncontrarChallengePeloId(){

        Challenge challenge = ChallengeFactory.get();
        Long challengeId = challenge.getId();

        doThrow(new ResponseStatusException(NOT_FOUND, "Challenge not found for this ID"))
                .when(challengeRepository)
                .findById(challengeId);

        assertThrows(ResponseStatusException.class, () -> tested.byId(challengeId));

        verify(challengeRepository).findById(challengeId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar Challenge pelo Sender e Receiver")
    void deveLancarExcecaoQuandoNaoEncontrarChallengePeloSenderReceiver(){

        Usuario sender = UsuarioFactory.get();
        Usuario receiver = UsuarioFactory.get();
        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        doThrow(new ResponseStatusException(NOT_FOUND, "Challenge not found with this users"))
                .when(challengeRepository)
                .findByUsuarioChallengeUsuarioSenderIdAndUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse
                        (senderId, receiverId);

        assertThrows(ResponseStatusException.class, () -> tested.bySenderAndReceiverId(senderId, receiverId));

        verify(challengeRepository)
                .findByUsuarioChallengeUsuarioSenderIdAndUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse
                        (senderId, receiverId);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchChallengeService {

    @Autowired
    private ChallengeRepository challengeRepository;

    public Challenge byId(Long challengeId) {

        return challengeRepository.findById(challengeId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Challenge not found for this ID"));
    }

    public Challenge bySenderAndReceiverId(Long senderId, Long receiverId) {

        return challengeRepository.findByUsuarioChallengeUsuarioSenderIdAndUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse
                        (senderId, receiverId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Challenge not found with this users"));
    }
}

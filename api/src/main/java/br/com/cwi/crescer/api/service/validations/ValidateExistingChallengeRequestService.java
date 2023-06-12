package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.repository.UsuarioChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class ValidateExistingChallengeRequestService {

    @Autowired
    private UsuarioChallengeRepository usuarioChallengeRepository;

    public void validate(Long usuarioSenderId, Long usuarioReceiverId) {

        if (usuarioChallengeRepository.existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse
                (usuarioSenderId, usuarioReceiverId)) {
            throw new ResponseStatusException(CONFLICT, "There is already a open challenge with this users");
        }
    }
}

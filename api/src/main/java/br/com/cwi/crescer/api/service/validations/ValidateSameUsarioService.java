package br.com.cwi.crescer.api.service.validations;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ValidateSameUsarioService {

    public void validateSend(Long usuarioId, Long usuarioChallengedId) {

        if(Objects.equals(usuarioId, usuarioChallengedId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Can't send a challenge request to yourself");
        }
    }

    public void validateReply(Long usuarioId, Long usuarioChallengingId) {

        if(Objects.equals(usuarioId, usuarioChallengingId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Can't reply a challenge request to yourself");
        }
    }
}

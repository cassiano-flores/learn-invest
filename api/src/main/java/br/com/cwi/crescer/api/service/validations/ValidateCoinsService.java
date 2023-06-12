package br.com.cwi.crescer.api.service.validations;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ValidateCoinsService {

    public void validate(Long userCoins, Long productPrice) {

        if(userCoins < productPrice) {
            throw new ResponseStatusException(BAD_REQUEST, "Insufficient coins to this purchase");
        }
    }
}

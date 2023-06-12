package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ValidateIconExistanceService {

    @Autowired
    private IconRepository iconRepository;

    public void validate(Long id) {

        if (!iconRepository.existsById(id)) {
            throw new ResponseStatusException(BAD_REQUEST, "Requested icon don´t exist");
        }
    }
}

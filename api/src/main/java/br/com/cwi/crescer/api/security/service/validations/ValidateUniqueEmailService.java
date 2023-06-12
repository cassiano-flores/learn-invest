package br.com.cwi.crescer.api.security.service.validations;

import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class ValidateUniqueEmailService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void validate(String email) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new ResponseStatusException(CONFLICT, "Email already exists");
        }
    }
}

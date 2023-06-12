package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchIconService {

    @Autowired
    private IconRepository iconRepository;

    public Icon byId(Long id) {
        return iconRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Icon not found"));
    }
}

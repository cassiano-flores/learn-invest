package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.mapper.IconMapper;
import br.com.cwi.crescer.api.repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListIconsService {

    @Autowired
    private IconRepository iconRepository;

    public Page<IconResponse> list(Pageable pageable) {

        return iconRepository.findAllByOrderById(pageable).map(IconMapper::toResponse);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListChallengeHistoryResponse;
import br.com.cwi.crescer.api.mapper.ListChallengeHistoryMapper;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListChallengeHistoryService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ChallengeRepository challengeRepository;

    public Page<ListChallengeHistoryResponse> list(Pageable pageable) {

        Usuario usuario = usuarioAutenticadoService.get();

        return challengeRepository
                .findAllByUsuarioChallengeUsuarioSenderIdOrUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsTrue
                        (usuario.getId(), usuario.getId(), pageable)
                .map(ListChallengeHistoryMapper::toResponse);
    }
}

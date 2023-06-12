package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListChallengeResponse;
import br.com.cwi.crescer.api.mapper.ListChallengeMapper;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ListOpenChallengeFromUsuarioService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private ChallengeRepository challengeRepository;

    public List<ListChallengeResponse> listSent() {

        Long usuarioId = usuarioAutenticadoService.getId();

        return challengeRepository.findAllByUsuarioChallengeUsuarioSenderIdAndIsCompleteIsFalse(usuarioId).stream()
                .map(ListChallengeMapper::toResponse)
                .collect(toList());
    }

    public List<ListChallengeResponse> listReceived() {

        Long usuarioId = usuarioAutenticadoService.getId();

        return challengeRepository.findByUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse(usuarioId).stream()
                .map(ListChallengeMapper::toResponse)
                .collect(toList());
    }
}

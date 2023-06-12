package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.ResultActivityRequest;
import br.com.cwi.crescer.api.controller.response.ReplyChallengeResponse;
import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.domain.UsuarioChallenge;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.repository.UsuarioChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.validations.ValidateExistingChallengeRequestService;
import br.com.cwi.crescer.api.service.validations.ValidateSameUsarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.mapper.ReplyChallengeMapper.toResponse;
import static java.time.LocalDateTime.now;

@Service
public class DoChallengeService {

    @Autowired
    private ValidateExistingChallengeRequestService validateExistingChallengeRequestService;

    @Autowired
    private ValidateSameUsarioService validateSameUsuarioService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private SearchChallengeService searchChallengeService;

    @Autowired
    private UsuarioChallengeRepository usuarioChallengeRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Transactional
    public void send(Long usuarioId, ResultActivityRequest request) {

        Usuario usuario = usuarioAutenticadoService.get();
        Usuario usuarioChallenged = searchUsuarioService.byId(usuarioId);

        validateSameUsuarioService.validateSend(usuario.getId(), usuarioId);
        validateExistingChallengeRequestService.validate(usuario.getId(), usuarioId);
        validateExistingChallengeRequestService.validate(usuarioId, usuario.getId());

        Challenge challenge = Challenge.builder()
                .usuarioSenderScore(request.getScore())
                .challengedIn(now())
                .isComplete(false)
                .build();

        UsuarioChallenge usuarioChallenge = UsuarioChallenge.builder()
                .usuarioSender(usuario)
                .usuarioReceiver(usuarioChallenged)
                .challenge(challenge)
                .build();

        challenge.setUsuarioChallenge(usuarioChallenge);

        usuarioChallengeRepository.save(usuarioChallenge);
        challengeRepository.save(challenge);
    }

    @Transactional
    public ReplyChallengeResponse reply(Long usuarioId, ResultActivityRequest request) {

        Usuario usuario = usuarioAutenticadoService.get();
        Challenge challenge = searchChallengeService.bySenderAndReceiverId(usuarioId, usuario.getId());

        validateSameUsuarioService.validateReply(usuario.getId(), usuarioId);

        challenge.setUsuarioReceiverScore(request.getScore());
        challengeRepository.save(challenge);

        return toResponse(challenge);
    }
}

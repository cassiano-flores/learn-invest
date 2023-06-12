package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Challenge;
import br.com.cwi.crescer.api.repository.ChallengeRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultChallengeService {

    @Autowired
    private SearchChallengeService searchChallengeService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static final int WINNER_LEAGUE_POINTS = 10;
    public static final int LOSER_LEAGUE_POINTS = 5;
    public static final int WINNER_COINS = 50;
    public static final int DRAW_COINS = 10;

    @Transactional
    public void result(Long challengeId) {

        Challenge challenge = searchChallengeService.byId(challengeId);
        Usuario usuarioSender = searchUsuarioService.byId(challenge.getUsuarioChallenge().getUsuarioSender().getId());
        Usuario usuarioReceiver = searchUsuarioService.byId(challenge.getUsuarioChallenge().getUsuarioReceiver().getId());

        if (challenge.getUsuarioSenderScore() > challenge.getUsuarioReceiverScore()) {
            usuarioSender.setLeaguePoints(usuarioSender.getLeaguePoints() + WINNER_LEAGUE_POINTS);
            usuarioSender.setCoins(usuarioSender.getCoins() + WINNER_COINS);
            usuarioReceiver.setLeaguePoints(Math.max(usuarioReceiver.getLeaguePoints() - LOSER_LEAGUE_POINTS, 0));
        }

        if (challenge.getUsuarioReceiverScore() > challenge.getUsuarioSenderScore()) {
            usuarioReceiver.setLeaguePoints(usuarioReceiver.getLeaguePoints() + WINNER_LEAGUE_POINTS);
            usuarioReceiver.setCoins(usuarioReceiver.getCoins() + WINNER_COINS);
            usuarioSender.setLeaguePoints(Math.max(usuarioSender.getLeaguePoints() - LOSER_LEAGUE_POINTS, 0));
        }

        if (challenge.getUsuarioReceiverScore().equals(challenge.getUsuarioSenderScore())) {
            usuarioSender.setCoins(usuarioSender.getCoins() + DRAW_COINS);
            usuarioReceiver.setCoins(usuarioReceiver.getCoins() + DRAW_COINS);
        }

        challenge.setComplete(true);
        challengeRepository.save(challenge);
        usuarioRepository.save(usuarioSender);
        usuarioRepository.save(usuarioReceiver);
    }
}

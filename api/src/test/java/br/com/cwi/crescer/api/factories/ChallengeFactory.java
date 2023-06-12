package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Challenge;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static java.time.LocalDateTime.now;

public class ChallengeFactory {

    public static Challenge get() {

        Challenge challenge = new Challenge();

        challenge.setId(getRandomLong());
        challenge.setUsuarioSenderScore(Math.abs(getRandomLong()));
        challenge.setUsuarioReceiverScore(Math.abs(getRandomLong()));
        challenge.setChallengedIn(now());
        challenge.setComplete(false);
        challenge.setUsuarioChallenge(UsuarioChallengeFactory.get());

        return challenge;
    }

    public static Challenge getSameUsuario() {

        Challenge challenge = new Challenge();

        challenge.setId(getRandomLong());
        challenge.setUsuarioSenderScore(getRandomLong());
        challenge.setUsuarioReceiverScore(getRandomLong());
        challenge.setChallengedIn(now());
        challenge.setComplete(false);
        challenge.setUsuarioChallenge(UsuarioChallengeFactory.getSameUsuario());

        return challenge;
    }
}

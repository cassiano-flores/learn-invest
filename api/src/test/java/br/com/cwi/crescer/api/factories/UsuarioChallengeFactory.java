package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.UsuarioChallenge;
import br.com.cwi.crescer.api.security.domain.Usuario;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class UsuarioChallengeFactory {

    public static UsuarioChallenge get() {

        UsuarioChallenge usuarioChallenge = new UsuarioChallenge();

        usuarioChallenge.setId(getRandomLong());
        usuarioChallenge.setUsuarioSender(UsuarioFactory.get());
        usuarioChallenge.setUsuarioReceiver(UsuarioFactory.get());

        return usuarioChallenge;
    }

    public static UsuarioChallenge getSameUsuario() {

        Usuario usuario = UsuarioFactory.get();
        UsuarioChallenge usuarioChallenge = new UsuarioChallenge();

        usuarioChallenge.setId(getRandomLong());
        usuarioChallenge.setUsuarioSender(usuario);
        usuarioChallenge.setUsuarioReceiver(usuario);

        return usuarioChallenge;
    }
}

package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Friendship;

public class FriendshipFactory {

    public static Friendship get(){

        Friendship friendship = new Friendship();
        friendship.setId(SimpleFactory.getRandomLong());
        friendship.setUsuarioId(SimpleFactory.getRandomLong());
        friendship.setFriend(UsuarioFactory.get());

        return friendship;
    }
}

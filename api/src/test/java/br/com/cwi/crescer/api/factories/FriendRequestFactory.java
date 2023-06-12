package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.FriendRequest;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class FriendRequestFactory {

    public static FriendRequest get() {

        FriendRequest friendRequest = new FriendRequest();

        friendRequest.setId(getRandomLong());
        friendRequest.setUsuarioSender(UsuarioFactory.get());
        friendRequest.setUsuarioReceiver(UsuarioFactory.get());

        return friendRequest;
    }
}

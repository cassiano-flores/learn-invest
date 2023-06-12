package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcceptFriendRequestService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private SearchFriendRequestService searchFriendRequestService;

    @Transactional
    public void accept(Long id) {
        Usuario authenticated = usuarioAutenticadoService.get();

        Usuario sender = searchUsuarioService.byId(id);

        FriendRequest friendRequest = searchFriendRequestService.findByUsersId(id, authenticated.getId());

        Friendship friendshipAuthenticated = new Friendship();
        Friendship friendshipSender = new Friendship();

        friendshipAuthenticated.setUsuarioId(authenticated.getId());
        friendshipAuthenticated.setFriend(sender);
        authenticated.addFriendship(friendshipAuthenticated);

        friendshipSender.setUsuarioId(sender.getId());
        friendshipSender.setFriend(authenticated);
        sender.addFriendship(friendshipSender);

        friendshipRepository.save(friendshipSender);
        friendshipRepository.save(friendshipAuthenticated);
        friendRequestRepository.delete(friendRequest);
    }
}

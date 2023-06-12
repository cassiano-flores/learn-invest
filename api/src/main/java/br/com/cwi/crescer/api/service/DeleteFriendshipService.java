package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteFriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private SearchFriendshipService searchFriendshipService;

    @Transactional
    public void delete(Long id) {

        Usuario authenticated = usuarioAutenticadoService.get();
        Usuario friend = searchUsuarioService.byId(id);

        Friendship authenticatedFriendship = searchFriendshipService.searchByUsersIds(authenticated.getId(), id);
        Friendship friendFriendship = searchFriendshipService.searchByUsersIds(id, authenticated.getId());

        authenticated.removeFriendship(authenticatedFriendship);
        friend.removeFriendship(friendFriendship);

        friendshipRepository.delete(authenticatedFriendship);
        friendshipRepository.delete(friendFriendship);
    }
}

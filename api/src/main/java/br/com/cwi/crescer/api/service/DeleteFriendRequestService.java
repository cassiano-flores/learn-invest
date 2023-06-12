package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteFriendRequestService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private SearchFriendRequestService searchFriendRequestService;

    public void delete(Long senderId) {

        Usuario usuario = usuarioAutenticadoService.get();

        FriendRequest request = searchFriendRequestService.findByUsersId(senderId, usuario.getId());

        friendRequestRepository.delete(request);
    }
}

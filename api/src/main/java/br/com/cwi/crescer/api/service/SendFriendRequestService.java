package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.validations.ValidateExistingFriendRequestService;
import br.com.cwi.crescer.api.service.validations.ValidateFriendshipExistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SendFriendRequestService {

    @Autowired
    private ValidateExistingFriendRequestService validateExistingFriendRequestService;

    @Autowired
    private ValidateFriendshipExistanceService validateFriendshipExistanceService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Transactional
    public void send(Long id) {
        Usuario authenticated = usuarioAutenticadoService.get();
        if(Objects.equals(authenticated.getId(), id)) {
            throw new ResponseStatusException(BAD_REQUEST, "Can't send a friend request to yourself");
        }

        Usuario requested = searchUsuarioService.byId(id);

        validateExistingFriendRequestService.validate(authenticated.getId(), requested.getId());
        validateFriendshipExistanceService.existbyIds(authenticated.getId(), requested.getId());

        FriendRequest friendRequest = FriendRequest.builder()
                .usuarioSender(authenticated)
                .usuarioReceiver(requested)
                .build();

        friendRequestRepository.save(friendRequest);
    }
}

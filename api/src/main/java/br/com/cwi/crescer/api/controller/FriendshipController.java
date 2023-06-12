package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.service.AcceptFriendRequestService;
import br.com.cwi.crescer.api.service.DeleteFriendRequestService;
import br.com.cwi.crescer.api.service.DeleteFriendshipService;
import br.com.cwi.crescer.api.service.SendFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @Autowired
    private SendFriendRequestService sendFriendRequestService;

    @Autowired
    private AcceptFriendRequestService acceptFriendRequestService;

    @Autowired
    private DeleteFriendRequestService deleteFriendRequestService;

    @Autowired
    private DeleteFriendshipService deleteFriendshipService;

    @PostMapping("/request/{usuarioId}")
    @ResponseStatus(OK)
    public void sendFriendRequest(@PathVariable Long usuarioId) {
        sendFriendRequestService.send(usuarioId);
    }

    @PostMapping("/accept/{usuarioId}")
    @ResponseStatus(OK)
    public void acceptFriendRequest(@PathVariable Long usuarioId) {
        acceptFriendRequestService.accept(usuarioId);
    }

    @DeleteMapping("/request/{usuarioId}")
    @ResponseStatus(OK)
    public void deleteFriendRequest(@PathVariable Long usuarioId) { deleteFriendRequestService.delete(usuarioId); }

    @DeleteMapping("/delete/{usuarioId}")
    @ResponseStatus(OK)
    public void deleteFriendship(@PathVariable Long usuarioId) {
        deleteFriendshipService.delete(usuarioId);
    }
}

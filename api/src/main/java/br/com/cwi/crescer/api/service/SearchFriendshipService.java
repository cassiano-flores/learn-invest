package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@Service
public class SearchFriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public Friendship searchByUsersIds(Long userId, Long friendId) {

        return friendshipRepository.findByUsuarioIdAndFriendId(userId, friendId)
                .orElseThrow(() -> new ResponseStatusException(BAD_REQUEST,
                        "Friendship or users don´t exist"));
    }
}

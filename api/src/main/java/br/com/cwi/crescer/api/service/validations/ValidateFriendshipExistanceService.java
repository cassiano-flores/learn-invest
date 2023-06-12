package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ValidateFriendshipExistanceService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public void existbyIds(Long userId, Long friendId) {

        Optional<Friendship> alreadyAFriend = friendshipRepository.findByUsuarioIdAndFriendId(userId, friendId);

        if(alreadyAFriend.isPresent()) {
            throw new ResponseStatusException(BAD_REQUEST,
                    "This friendship already exists!");
        }
    }
}

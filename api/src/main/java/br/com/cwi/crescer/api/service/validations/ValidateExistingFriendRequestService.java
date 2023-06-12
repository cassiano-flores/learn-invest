package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
public class ValidateExistingFriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public void validate(Long senderId, Long receiverId) {

        if ((friendRequestRepository.findExistingFriendRequest(senderId, receiverId)).isPresent()) {
            throw new ResponseStatusException(CONFLICT, "This friendship request already exists");
        }
    }
}

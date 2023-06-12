package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class SearchFriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public FriendRequest findByUsersId(Long senderId, Long receiverId) {

        return friendRequestRepository.findByUsuarioSenderIdAndUsuarioReceiverId(senderId, receiverId)
                .orElseThrow(() ->
                        new ResponseStatusException(BAD_REQUEST,
                                "Friendship request not found"));
    }
}

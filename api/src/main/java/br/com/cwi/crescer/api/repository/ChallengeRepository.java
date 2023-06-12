package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    List<Challenge> findAllByOrderById();

    List<Challenge> findAllByUsuarioChallengeUsuarioSenderIdAndIsCompleteIsFalse(Long usuarioSenderId);

    List<Challenge> findByUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse(Long usuarioReceiverId);

    Optional<Challenge> findByUsuarioChallengeUsuarioSenderIdAndUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsFalse
            (Long senderId, Long receiverId);

    Page<Challenge> findAllByUsuarioChallengeUsuarioSenderIdOrUsuarioChallengeUsuarioReceiverIdAndIsCompleteIsTrue
            (Long senderId, Long receiverId, Pageable pageable);
}

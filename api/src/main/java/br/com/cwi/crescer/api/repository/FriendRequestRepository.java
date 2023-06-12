package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    List<FriendRequest> findByUsuarioSender(Usuario usuario);

    List<FriendRequest> findByUsuarioReceiver(Usuario usuario);

    List<FriendRequest> findAllByOrderById();

    Optional<FriendRequest> findByUsuarioSenderIdAndUsuarioReceiverId(Long senderId, Long receiverId);

    @Query(value = "SELECT sol FROM FriendRequest sol " +
            "INNER JOIN Usuario users " +
            "ON users.id = sol.usuarioSender.id " +
            "INNER JOIN Usuario users2 " +
            "ON users2.id = sol.usuarioReceiver.id " +
            "WHERE 1=1 " +
            "AND (sol.usuarioSender.id = :senderId AND sol.usuarioReceiver.id = :receiverId " +
            "OR sol.usuarioSender.id = :receiverId AND sol.usuarioReceiver.id = :senderId) ")
    Optional<FriendRequest> findExistingFriendRequest(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);
}

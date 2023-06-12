package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Page<Friendship> findByUsuarioIdOrderByFriendNameAsc(Long id, Pageable pageable);

    List<Friendship> findAllFriendsIdsByUsuarioId(Long usuarioId);

    Optional<Friendship> findByUsuarioIdAndFriendId(Long id, Long id1);

    List<Friendship> findAllByOrderById();
}

package br.com.cwi.crescer.api.security.repository;

import br.com.cwi.crescer.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByToken(String token);

    Page<Usuario> findByNameContainsIgnoreCaseOrEmailContainsIgnoreCase(String text, String text1, Pageable pageable);

    Page<Usuario> findByIdInOrderByLeaguePointsDesc(List<Long> ids, Pageable pageable);
}

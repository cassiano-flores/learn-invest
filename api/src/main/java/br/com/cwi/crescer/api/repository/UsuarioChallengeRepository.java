package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.UsuarioChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioChallengeRepository extends JpaRepository<UsuarioChallenge, Long> {

    boolean existsByUsuarioSenderIdAndUsuarioReceiverIdAndChallengeIsCompleteIsFalse
            (Long usuarioSenderId, Long usuarioReceiverId);
}

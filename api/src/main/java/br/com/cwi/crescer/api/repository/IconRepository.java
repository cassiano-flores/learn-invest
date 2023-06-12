package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Icon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<Icon, Long> {

    Page<Icon> findAllByOrderById(Pageable pageable);
}

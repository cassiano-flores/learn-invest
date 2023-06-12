package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    List<Achievement> findAllByOrderById();
}

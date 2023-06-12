package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findAllByOrderById();
}

package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAllByOrderById();
}

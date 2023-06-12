package br.com.cwi.crescer.api.repository;

import br.com.cwi.crescer.api.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByOrderById();
}

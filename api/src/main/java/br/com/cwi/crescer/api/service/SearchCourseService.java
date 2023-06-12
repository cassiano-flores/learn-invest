package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchCourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course byId(Long courseId) {

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Course not found for this ID"));
    }
}

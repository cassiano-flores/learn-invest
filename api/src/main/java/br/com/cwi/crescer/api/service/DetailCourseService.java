package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.DetailCourseMapper.toResponse;

@Service
public class DetailCourseService {

    @Autowired
    private SearchCourseService searchCourseService;

    public CourseResponse detail(Long courseId) {

        return toResponse(searchCourseService.byId(courseId));
    }
}

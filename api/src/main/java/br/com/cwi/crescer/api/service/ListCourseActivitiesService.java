package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListCourseActivitiesResponse;
import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.mapper.ListCourseActivitiesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ListCourseActivitiesService {

    @Autowired
    private SearchCourseService searchCourseService;

    public List<ListCourseActivitiesResponse> list(Long courseId) {
        Course course = searchCourseService.byId(courseId);

        return course.getActivities().stream()
                .map(ListCourseActivitiesMapper::toResponse)
                .collect(toList());
    }
}

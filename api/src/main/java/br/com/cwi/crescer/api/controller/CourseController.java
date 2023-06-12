package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.controller.response.ListCourseActivitiesResponse;
import br.com.cwi.crescer.api.service.DetailCourseService;
import br.com.cwi.crescer.api.service.ListCourseActivitiesService;
import br.com.cwi.crescer.api.service.ListCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private ListCourseService listCourseService;

    @Autowired
    private ListCourseActivitiesService listCourseActivitiesService;

    @Autowired
    private DetailCourseService detailCourseService;

    @GetMapping
    @ResponseStatus(OK)
    public List<CourseResponse> listCourses() {
        return listCourseService.list();
    }

    @GetMapping("/available")
    @ResponseStatus(OK)
    public List<CourseResponse> listCoursesAvailable() {
        return listCourseService.listAvailable();
    }

    @GetMapping("/{courseId}")
    @ResponseStatus(OK)
    public CourseResponse detailCourse(@PathVariable Long courseId) {
        return detailCourseService.detail(courseId);
    }

    @GetMapping("/{courseId}/activities")
    @ResponseStatus(OK)
    public List<ListCourseActivitiesResponse> listActivities(@PathVariable Long courseId) {
        return listCourseActivitiesService.list(courseId);
    }
}

package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.CourseResponse;
import br.com.cwi.crescer.api.domain.Course;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CourseMapper {

    public static CourseResponse toResponse(Course course) {

        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .nextCourseId(course.getNextCourseId())
                .achievementCompleteCourseId(course.getAchievementCompleteCourseId())
                .build();
    }
}

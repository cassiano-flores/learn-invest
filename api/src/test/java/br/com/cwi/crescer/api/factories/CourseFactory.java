package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Course;

import java.util.ArrayList;
import java.util.List;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class CourseFactory {

    public static Course get() {

        List<Activity> activities = new ArrayList<>();
        activities.add(ActivityFactory.getNoCourse());
        activities.add(ActivityFactory.getNoCourse());
        activities.add(ActivityFactory.getNoCourse());
        activities.add(ActivityFactory.getNoCourse());
        activities.add(ActivityFactory.getNoCourse());
        activities.add(ActivityFactory.getNoCourse());

        Course course = new Course();

        course.setId(getRandomLong());
        course.setName("Test Course");
        course.setNextCourseId(getRandomLong());
        course.setAchievementCompleteCourseId(getRandomLong());
        course.setActivities(activities);

        return course;
    }

    public static Course getNoActivity() {

        Course course = new Course();

        course.setId(getRandomLong());
        course.setName("Test Course");
        course.setNextCourseId(getRandomLong());
        course.setAchievementCompleteCourseId(getRandomLong());
        course.setActivities(new ArrayList<>());

        return course;
    }
}

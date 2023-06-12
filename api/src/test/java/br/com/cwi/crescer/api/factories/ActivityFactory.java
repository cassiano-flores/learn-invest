package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Question;

import java.util.List;
import java.util.ArrayList;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class ActivityFactory {

    public static Activity get() {

        List<Question> questions = new ArrayList<>();
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());

        Activity activity = new Activity();
        activity.setId(getRandomLong());
        activity.setTitle("Test");
        activity.setLesson("Test Activity");
        activity.setCourse(CourseFactory.get());
        activity.setQuestions(questions);

        return activity;
    }

    public static Activity getNoCourse() {

        List<Question> questions = new ArrayList<>();
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());
        questions.add(QuestionFactory.getNoActivity());

        Activity activity = new Activity();
        activity.setId(getRandomLong());
        activity.setTitle("Test");
        activity.setLesson("Test Activity");
        activity.setQuestions(questions);

        return activity;
    }
}

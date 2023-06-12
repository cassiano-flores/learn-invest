package br.com.cwi.crescer.api.factories;

import br.com.cwi.crescer.api.domain.Question;

import static br.com.cwi.crescer.api.domain.enums.AnswerOptions.A;
import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;

public class QuestionFactory {

    public static Question get() {

        Question question = new Question();

        question.setId(getRandomLong());
        question.setQuestionText("Test Question");
        question.setAlternativeA("Test A");
        question.setAlternativeB("Test B");
        question.setAlternativeC("Test C");
        question.setAlternativeD("Test D");
        question.setAnswer(A);
        question.setActivity(ActivityFactory.get());

        return question;
    }

    public static Question getNoActivity() {

        Question question = new Question();

        question.setId(getRandomLong());
        question.setQuestionText("Test Question");
        question.setAlternativeA("Test A");
        question.setAlternativeB("Test B");
        question.setAlternativeC("Test C");
        question.setAlternativeD("Test D");
        question.setAnswer(A);

        return question;
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.mapper.ListActivityQuestionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class ListActivityQuestionsService {

    @Autowired
    private SearchActivityService searchActivityService;

    private final Random random = new Random();

    public List<ListActivityQuestionsResponse> listAllQuestions(Long activityId) {
        Activity activity = searchActivityService.byId(activityId);

        return activity.getQuestions().stream()
                .map(ListActivityQuestionsMapper::toResponse)
                .collect(toList());
    }

    public List<ListActivityQuestionsResponse> listFiveQuestions(Long activityId) {
        Activity activity = searchActivityService.byId(activityId);

        List<Integer> allIndices = IntStream.range(0, activity.getQuestions().size()).boxed().collect(toList());
        Collections.shuffle(allIndices, random);

        int endIndex = Math.min(allIndices.size(), 5);
        List<Integer> randomIndices = allIndices.subList(0, endIndex);

        return randomIndices.stream()
                .map(activity.getQuestions()::get)
                .map(ListActivityQuestionsMapper::toResponse)
                .collect(toList());
    }
}

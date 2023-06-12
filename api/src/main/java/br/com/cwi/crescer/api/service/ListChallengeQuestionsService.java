package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.mapper.ListActivityQuestionsMapper;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
public class ListChallengeQuestionsService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    private final Random random = new Random();

    public List<ListActivityQuestionsResponse> listFiveQuestions() {

        Usuario usuario = usuarioAutenticadoService.get();
        List<Activity> activities;

        if (usuario.getFinishedActivities().isEmpty()) {
            activities = usuario.getActivities();
        } else {
            activities = usuario.getFinishedActivities();
        }

        List<Question> allQuestions = activities.stream()
                .flatMap(activity -> activity.getQuestions().stream())
                .collect(toList());

        List<Integer> allIndices = IntStream.range(0, allQuestions.size()).boxed().collect(toList());
        Collections.shuffle(allIndices, random);

        int endIndex = Math.min(allIndices.size(), 5);
        List<Integer> randomIndices = allIndices.subList(0, endIndex);

        return randomIndices.stream()
                .map(allQuestions::get)
                .map(ListActivityQuestionsMapper::toResponse)
                .collect(toList());
    }
}

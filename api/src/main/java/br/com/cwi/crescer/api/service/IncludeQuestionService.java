package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.IncludeQuestionRequest;
import br.com.cwi.crescer.api.controller.response.QuestionResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.mapper.QuestionMapper.toEntity;
import static br.com.cwi.crescer.api.mapper.QuestionMapper.toResponse;

@Service
public class IncludeQuestionService {

    @Autowired
    private SearchActivityService searchActivityService;

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    public QuestionResponse include(IncludeQuestionRequest request) {

        Activity activity = searchActivityService.byId(request.getActivityId());
        Question question = toEntity(request);

        question.setActivity(activity);

        questionRepository.save(question);

        return toResponse(question);
    }
}

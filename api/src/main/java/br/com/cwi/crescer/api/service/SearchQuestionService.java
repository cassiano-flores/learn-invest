package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchQuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question byId(Long questionId) {

        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Question not found for this ID"));
    }
}

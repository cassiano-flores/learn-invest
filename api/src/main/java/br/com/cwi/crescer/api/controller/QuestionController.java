package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.AnswerQuestionRequest;
import br.com.cwi.crescer.api.controller.request.IncludeQuestionRequest;
import br.com.cwi.crescer.api.controller.response.AnswerQuestionResponse;
import br.com.cwi.crescer.api.controller.response.QuestionResponse;
import br.com.cwi.crescer.api.service.AnswerQuestionService;
import br.com.cwi.crescer.api.service.IncludeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private AnswerQuestionService answerQuestionService;

    @Autowired
    private IncludeQuestionService includeQuestionService;

    @PostMapping("/{questionId}")
    @ResponseStatus(OK)
    public AnswerQuestionResponse answer
            (@PathVariable Long questionId, @Valid @RequestBody AnswerQuestionRequest request) {
        return answerQuestionService.answer(questionId, request);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(CREATED)
    public QuestionResponse include(@Valid @RequestBody IncludeQuestionRequest request) {
        return includeQuestionService.include(request);
    }
}

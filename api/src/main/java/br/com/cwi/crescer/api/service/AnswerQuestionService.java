package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.request.AnswerQuestionRequest;
import br.com.cwi.crescer.api.controller.response.AnswerQuestionResponse;
import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.domain.Question;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.cwi.crescer.api.mapper.AnswerQuestionMapper.toResponse;

@Service
public class AnswerQuestionService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private SearchQuestionService searchQuestionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public static final int XP_EARNED_PER_CORRECT_ANSWER = 60;
    public static final int XP_EARNED_PER_CORRECT_ANSWER_PRACTICED = 10;

    @Transactional
    public AnswerQuestionResponse answer(Long questionId, AnswerQuestionRequest request) {

        Usuario usuario = usuarioAutenticadoService.get();
        Question question = searchQuestionService.byId(questionId);
        Activity activity = question.getActivity();

        boolean isCorrect = request.getAnswer() == question.getAnswer();

        if (isCorrect) {
            if (usuario.getFinishedActivities().contains(activity)) {
                usuario.setXp(usuario.getXp() + XP_EARNED_PER_CORRECT_ANSWER_PRACTICED);
            } else {
                usuario.setXp(usuario.getXp() + XP_EARNED_PER_CORRECT_ANSWER);
            }
            usuarioRepository.save(usuario);
        }

        return toResponse(isCorrect);
    }
}

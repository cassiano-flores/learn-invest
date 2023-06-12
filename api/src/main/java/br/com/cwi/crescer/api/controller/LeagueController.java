package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.ResultActivityRequest;
import br.com.cwi.crescer.api.controller.response.*;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/league")
public class LeagueController {

    @Autowired
    private ListLeagueService listLeagueService;

    @Autowired
    private ListOpenChallengeFromUsuarioService listChallengeService;

    @Autowired
    private ListChallengeQuestionsService listChallengeQuestionsService;

    @Autowired
    private DoChallengeService doChallengeService;

    @Autowired
    private ResultChallengeService resultChallengeService;

    @Autowired
    private ListChallengeHistoryService listChallengeHistoryService;

    @GetMapping
    @ResponseStatus(OK)
    public Page<FriendshipResponse> listRank(Pageable pageable) {
        return listLeagueService.list(pageable);
    }

    @GetMapping("/challenges/sent")
    @ResponseStatus(OK)
    public List<ListChallengeResponse> challengesSent() {
        return listChallengeService.listSent();
    }

    @GetMapping("/challenges/received")
    @ResponseStatus(OK)
    public List<ListChallengeResponse> challengesReceived() {
        return listChallengeService.listReceived();
    }

    @GetMapping("/questions")
    @ResponseStatus(OK)
    public List<ListActivityQuestionsResponse> listFiveQuestions() {
        return listChallengeQuestionsService.listFiveQuestions();
    }

    @PostMapping("/challenge/{usuarioId}")
    @ResponseStatus(CREATED)
    public void sendChallenge(@PathVariable Long usuarioId, @Valid @RequestBody ResultActivityRequest request) {
        doChallengeService.send(usuarioId, request);
    }

    @PutMapping("/challenge/{usuarioId}")
    @ResponseStatus(OK)
    public ReplyChallengeResponse replyChallenge(@PathVariable Long usuarioId, @Valid @RequestBody ResultActivityRequest request) {
        return doChallengeService.reply(usuarioId, request);
    }

    @PutMapping("/challenge/{challengeId}/result")
    @ResponseStatus(OK)
    public void resultChallenge(@PathVariable Long challengeId) {
        resultChallengeService.result(challengeId);
    }

    @GetMapping("/history")
    @ResponseStatus(OK)
    public Page<ListChallengeHistoryResponse> listHistory(Pageable pageable) {
        return listChallengeHistoryService.list(pageable);
    }
}

package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.ActivityAvailableResponse;
import br.com.cwi.crescer.api.controller.response.ActivityResponse;
import br.com.cwi.crescer.api.controller.response.ListActivityQuestionsResponse;
import br.com.cwi.crescer.api.service.DetailActivityService;
import br.com.cwi.crescer.api.service.ListActivityQuestionsService;
import br.com.cwi.crescer.api.service.ListActivityService;
import br.com.cwi.crescer.api.service.ResultActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ListActivityService listActivityService;

    @Autowired
    private ListActivityQuestionsService listActivityQuestionsService;

    @Autowired
    private DetailActivityService detailActivityService;

    @Autowired
    private ResultActivityService resultActivityService;

    @GetMapping
    @ResponseStatus(OK)
    public List<ActivityResponse> listActivities() {
        return listActivityService.list();
    }

    @GetMapping("/available")
    @ResponseStatus(OK)
    public List<ActivityResponse> listActivitiesAvailable() {
        return listActivityService.listAvailable();
    }

    @GetMapping("/{activityId}")
    @ResponseStatus(OK)
    public ActivityAvailableResponse detailActivity(@PathVariable Long activityId) {
        return detailActivityService.detail(activityId);
    }

    @GetMapping("/{activityId}/questions")
    @ResponseStatus(OK)
    public List<ListActivityQuestionsResponse> listAllQuestions(@PathVariable Long activityId) {
        return listActivityQuestionsService.listAllQuestions(activityId);
    }

    @GetMapping("/{activityId}/practice")
    @ResponseStatus(OK)
    public List<ListActivityQuestionsResponse> listFiveQuestions(@PathVariable Long activityId) {
        return listActivityQuestionsService.listFiveQuestions(activityId);
    }

    @PutMapping("/{activityId}/finish-activity")
    @ResponseStatus(OK)
    public void results(@PathVariable Long activityId) {
        resultActivityService.result(activityId);
    }
}

package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Activity;
import br.com.cwi.crescer.api.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity byId(Long activityId) {

        return activityRepository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Activity not found for this ID"));
    }
}

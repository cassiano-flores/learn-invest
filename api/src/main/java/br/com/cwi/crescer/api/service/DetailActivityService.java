package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.ActivityAvailableResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.cwi.crescer.api.mapper.ListActivityAvailableMapper.toResponse;

@Service
public class DetailActivityService {

    @Autowired
    private SearchActivityService searchActivityService;

    public ActivityAvailableResponse detail(Long activityId) {

        return toResponse(searchActivityService.byId(activityId));
    }
}

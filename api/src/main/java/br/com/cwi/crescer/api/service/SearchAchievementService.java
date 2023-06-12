package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Achievement;
import br.com.cwi.crescer.api.repository.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SearchAchievementService {

    @Autowired
    private AchievementRepository achievementRepository;

    public Achievement byId(Long achievementId) {

        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Achievement not found for this ID"));
    }
}

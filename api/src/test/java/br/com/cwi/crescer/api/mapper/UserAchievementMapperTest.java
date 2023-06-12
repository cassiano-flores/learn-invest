package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.UserAchievementResponse;
import br.com.cwi.crescer.api.domain.Achievement;
import br.com.cwi.crescer.api.factories.UserAchievementFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserAchievementMapperTest {

    @Test
    @DisplayName("Deve mapear corretamente uma Achievement para AchievementResponse")
    void DeveMapearCorretamenteUmaAchievementParaAchievementResponse(){
        Achievement achievement = UserAchievementFactory.get();

        UserAchievementResponse response = UserAchievementMapper.toResponse(achievement);

        assertEquals(response.getName(), achievement.getName());
        assertEquals(response.getDescription(), achievement.getDescription());
        assertEquals(response.getIcon(), achievement.getIcon());
    }
}

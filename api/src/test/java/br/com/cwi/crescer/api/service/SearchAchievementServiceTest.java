package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Achievement;
import br.com.cwi.crescer.api.factories.AchievementFactory;
import br.com.cwi.crescer.api.repository.AchievementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class SearchAchievementServiceTest {

    @InjectMocks
    private SearchAchievementService tested;

    @Mock
    private AchievementRepository achievementRepository;

    @Test
    @DisplayName("Deve retornar conquista com sucesso")
    void deveRetornarConquistaComSucesso() {

        Achievement achievement = AchievementFactory.get();
        Long achievementId = achievement.getId();

        when(achievementRepository.findById(achievementId)).thenReturn(Optional.of(achievement));

        Achievement achievementExpected = tested.byId(achievementId);

        verify(achievementRepository).findById(achievementId);

        assertEquals(achievementExpected, achievement);
    }

    @Test
    @DisplayName("Deve lançar exceção quando conquista não for encontrada")
    void deveLancarExcecaoQuandoConquistaNaoForEncontrada() {

        Achievement achievement = AchievementFactory.get();
        Long achievementId = achievement.getId();

        when(achievementRepository.findById(achievementId)).thenThrow(new ResponseStatusException(NOT_FOUND, "Achievement not found for this ID"));

        assertThrows(ResponseStatusException.class, () -> tested.byId(achievementId));

        verify(achievementRepository).findById(achievementId);
    }
}

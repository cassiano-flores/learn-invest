package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.repository.IconRepository;
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

@ExtendWith(MockitoExtension.class)
public class SearchIconServiceTest {

    @InjectMocks
    private SearchIconService tested;

    @Mock
    private IconRepository iconRepository;

    @Test
    @DisplayName("Deve retornar icone com id informado")
    void deveRetornarIconeComIdInformado() {

        Icon icon = new Icon();

        icon.setId(1L);
        Long iconId = icon.getId();

        when(iconRepository.findById(iconId)).thenReturn(Optional.of(icon));

        Icon result = tested.byId(iconId);

        verify(iconRepository).findById(iconId);

        assertEquals(icon.getId(), result.getId());
    }


    @Test
    @DisplayName("Deve lancar exceção ao não encontrar icone com determinada id")
    void deveLancarExcecaoAoNaoEncotrarId() {

        Long id = SimpleFactory.getRandomLong();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.byId(id));

        assertEquals("Icon not found", exception.getReason());
    }
}
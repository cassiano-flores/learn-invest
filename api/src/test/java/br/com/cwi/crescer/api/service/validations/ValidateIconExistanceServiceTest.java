package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.factories.SimpleFactory;
import br.com.cwi.crescer.api.repository.IconRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidateIconExistanceServiceTest {

    @InjectMocks
    private ValidateIconExistanceService tested;

    @Mock
    private IconRepository iconRepository;

    @Test
    @DisplayName("Deve lançar exceção quando o id fornecido não corresponder a um icone")
    void deveLancarExcecaoQuandoNaoExistirIcone() {

        Long id = SimpleFactory.getRandomLong();
        when(iconRepository.existsById(id)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.validate(id));

        assertEquals("Requested icon don´t exist", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando o id fornecido corresponder a um icone")
    void naoDeveLancarExcecaoQuandoExistirIcone() {
        Long id = SimpleFactory.getRandomLong();
        when(iconRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> tested.validate(id));
    }
}

package br.com.cwi.crescer.api.service.validations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidateCoinsServiceTest {

    @InjectMocks
    private ValidateCoinsService tested;

    @Test
    @DisplayName("Deve lançar exceção quando usuario não possuir moedas suficientes para comprar produto")
    void deveLancarExcecaoQuandoUsuarioNaoPossuirMoedasParaComprarProduto() {

        Long coins = 20L;
        Long price = 21L;

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.validate(coins, price));

        assertEquals("Insufficient coins to this purchase", exception.getReason());
    }

    @Test
    @DisplayName("Não deve lançar exceção quando usuário possui moedas suficientes para comprar produto")
    void naoDeveLancarExcecaoQuandoUsuarioPossuiMoedasParaComprarProduto() {

        Long coins = 21L;
        Long price = 20L;

        assertDoesNotThrow(() -> tested.validate(coins, price));
    }
}

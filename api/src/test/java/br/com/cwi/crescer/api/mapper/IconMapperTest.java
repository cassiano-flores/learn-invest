package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.factories.IconFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class IconMapperTest {

    @Test
    @DisplayName("Deve mapear um icon para iconResponse")
    void deveMapearIconParaIconResponse() {

        Icon icon = IconFactory.get();

        IconResponse response = IconMapper.toResponse(icon);

        assertEquals(icon.getId(), response.getId());
        assertEquals(icon.getName(), response.getName());
        assertEquals(icon.getPrice(), response.getPrice());
    }
}

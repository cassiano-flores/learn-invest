package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.factories.IconFactory;
import br.com.cwi.crescer.api.mapper.IconMapper;
import br.com.cwi.crescer.api.repository.IconRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListIconsServiceTest {

    @InjectMocks
    private ListIconsService tested;

    @Mock
    private IconRepository iconsRepository;

    @Test
    @DisplayName("Deve listar todas os icones")
    void deveListarTodasOsIcones() {

        List<Icon> icons = List.of(
                IconFactory.get(),
                IconFactory.get(),
                IconFactory.get()
        );

        Pageable pageable = PageRequest.of(0,5);
        Page<Icon> iconsPage = new PageImpl<>(icons);
        Page<IconResponse> iconsResponsePage = new PageImpl<>(iconsPage.stream()
                .map(IconMapper::toResponse).collect(Collectors.toList()));

        when(iconsRepository.findAllByOrderById(pageable)).thenReturn(iconsPage);

        Page<IconResponse> iconsResponse = tested.list(pageable);

        verify(iconsRepository).findAllByOrderById(pageable);

        assertEquals(iconsResponsePage.getSize(), iconsResponse.getSize());

        for (Icon icon : icons) {
            boolean found = false;
            for (IconResponse iconResponse : iconsResponse.getContent()) {
                if (iconResponse.getId().equals(icon.getId())) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }
}

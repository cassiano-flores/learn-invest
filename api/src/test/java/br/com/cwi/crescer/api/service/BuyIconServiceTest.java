package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.factories.IconFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.validations.ValidateCoinsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BuyIconServiceTest {

    @InjectMocks
    private BuyIconService tested;

    @Mock
    private SearchIconService searchIconService;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ValidateCoinsService validateCoinsService;


    @Test
    @DisplayName("Deve equipar icone já possuído sem gastar moedas")
    void deveEquiparIconeJaPossuido() {

        Icon icon = IconFactory.get();
        Long iconId = icon.getId();
        Usuario user = UsuarioFactory.get();
        user.getIcons().add(icon);

        user.setCoins(10L);

        when(usuarioAutenticadoService.get()).thenReturn(user);
        when(searchIconService.byId(iconId)).thenReturn(icon);

        tested.buy(iconId);

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(iconId);
        verify(usuarioRepository).save(user);

        assertEquals(user.getCurrentIcon().getId(), iconId);
        assertEquals(user.getCoins(), 10L);
    }

    @Test
    @DisplayName("Deve comprar e equipar icone gastando moedas")
    void deveComprarEEquiparIcone() {

        Icon icon = IconFactory.get();
        icon.setId(1L);
        icon.setPrice(100L);
        Long iconId = icon.getId();
        Usuario user = UsuarioFactory.get();

        user.setCoins(100L);

        when(usuarioAutenticadoService.get()).thenReturn(user);
        when(searchIconService.byId(iconId)).thenReturn(icon);

        tested.buy(iconId);

        verify(usuarioAutenticadoService).get();
        verify(searchIconService).byId(iconId);
        verify(validateCoinsService).validate(100L, icon.getPrice());
        verify(usuarioRepository).save(user);

        assertEquals(user.getCurrentIcon().getId(), iconId);
        assertEquals(user.getIcons().get(0).getId(), iconId);
        assertEquals(user.getCoins(), 0L);
    }
}

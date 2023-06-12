package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.validations.ValidateCoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuyIconService {

    @Autowired
    private SearchIconService searchIconService;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ValidateCoinsService validateCoinsService;

    @Transactional
    public void buy(Long id) {

        Usuario user = usuarioAutenticadoService.get();
        Icon icon = searchIconService.byId(id);

        if(user.getIcons().contains(icon)) {

            user.setCurrentIcon(icon);

        } else {
            validateCoinsService.validate(user.getCoins(), icon.getPrice());
            user.addIcon(icon);
            user.setCurrentIcon(icon);
            user.setCoins(user.getCoins() - icon.getPrice());
        }

        usuarioRepository.save(user);
    }
}

package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.service.BuyIconService;
import br.com.cwi.crescer.api.service.ListIconsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private ListIconsService listIconsService;

    @Autowired
    private BuyIconService buyIconService;

    @GetMapping("/icons")
    @ResponseStatus(OK)
    public Page<IconResponse> listIcons(Pageable pageable) {
        return listIconsService.list(pageable);
    }

    @PutMapping("/icons/{iconId}")
    @ResponseStatus(NO_CONTENT)
    public void buyIcon(@PathVariable Long iconId) {
        buyIconService.buy(iconId);
    }
}

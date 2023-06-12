package br.com.cwi.crescer.api.security.controller;

import br.com.cwi.crescer.api.security.controller.request.ForgotPasswordRequest;
import br.com.cwi.crescer.api.security.controller.request.ResetPasswordRequest;
import br.com.cwi.crescer.api.security.controller.request.UsuarioRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.service.BuscarUsuarioService;
import br.com.cwi.crescer.api.security.service.ForgotPasswordService;
import br.com.cwi.crescer.api.security.service.IncluirUsuarioService;
import br.com.cwi.crescer.api.security.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
public class UsuarioController {

    @Autowired
    private IncluirUsuarioService incluirUsuarioService;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public UsuarioResponse incluir(@Valid @RequestBody UsuarioRequest request) {
        return incluirUsuarioService.incluir(request);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public UsuarioResponse login() {
        return buscarUsuarioService.buscar();
    }

    @PostMapping("/logout")
    @ResponseStatus(NO_CONTENT)
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @PostMapping("/forgot-password")
    @ResponseStatus(NO_CONTENT)
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) throws MessagingException {
        forgotPasswordService.generateResetToken(request);
    }

    @PutMapping("/reset-password")
    @ResponseStatus(OK)
    public UsuarioResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request, @RequestParam String token) {
        return resetPasswordService.reset(request, token);
    }
}

package br.com.cwi.crescer.api.service.validations;

import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.validations.ValidateUniqueEmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.CONFLICT;

@ExtendWith(MockitoExtension.class)
class ValidateUniqueEmailServiceTest {

    @InjectMocks
    private ValidateUniqueEmailService tested;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve lançar exceção quando o email não for único")
    void deveLancarExcecaoQuandoEmailNaoForUnico() {

        Usuario usuario = UsuarioFactory.get();
        String email = usuario.getEmail();

        doThrow(new ResponseStatusException(CONFLICT)).when(usuarioRepository).existsByEmail(email);

        assertThrows(ResponseStatusException.class, () -> tested.validate(email));

        verify(usuarioRepository).existsByEmail(email);
    }
}

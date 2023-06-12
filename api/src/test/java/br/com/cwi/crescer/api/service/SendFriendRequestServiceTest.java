package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import br.com.cwi.crescer.api.service.validations.ValidateExistingFriendRequestService;
import br.com.cwi.crescer.api.service.validations.ValidateFriendshipExistanceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SendFriendRequestServiceTest {

    @InjectMocks
    private SendFriendRequestService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private SearchUsuarioService searchUsuarioService;

    @Mock
    private FriendRequestRepository friendRequestRepository;

    @Mock
    private ValidateExistingFriendRequestService validateExistingFriendRequestService;

    @Mock
    private ValidateFriendshipExistanceService validateFriendshipExistanceService;

    @Captor
    private ArgumentCaptor<FriendRequest> friendRequestCaptor;

    @Test
    @DisplayName("Deve enviar solicitação de amizade")
    void deveEnviarSolicitacaoDeAmizade() {

        Usuario sender = UsuarioFactory.get();
        sender.setId(1L);
        Usuario receiver = UsuarioFactory.get();
        receiver.setId(2L);

        when(usuarioAutenticadoService.get()).thenReturn(sender);
        when(searchUsuarioService.byId(receiver.getId())).thenReturn(receiver);

        tested.send(receiver.getId());

        verify(usuarioAutenticadoService).get();
        verify(searchUsuarioService).byId(receiver.getId());
        verify(validateExistingFriendRequestService).validate(sender.getId(), receiver.getId());
        verify(validateFriendshipExistanceService).existbyIds(sender.getId(), receiver.getId());
        verify(friendRequestRepository).save(friendRequestCaptor.capture());

        FriendRequest friendRequest = friendRequestCaptor.getValue();
        assertEquals(sender.getId(), friendRequest.getUsuarioSender().getId());
        assertEquals(receiver.getId(), friendRequest.getUsuarioReceiver().getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar enviar pedido de amizade para si")
    void deveLancarExcecaoEmAutoPedidoDeAmizade() {

        Usuario sender = UsuarioFactory.get();
        sender.setId(1L);

        when(usuarioAutenticadoService.get()).thenReturn(sender);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                tested.send(sender.getId()));

        verify(usuarioAutenticadoService).get();

        assertEquals("Can't send a friend request to yourself", exception.getReason());
    }
}

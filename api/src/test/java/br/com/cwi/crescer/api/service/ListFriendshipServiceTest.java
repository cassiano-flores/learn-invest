package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.FriendRequest;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.factories.FriendshipFactory;
import br.com.cwi.crescer.api.factories.UsuarioFactory;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.factories.SimpleFactory.getRandomLong;
import static br.com.cwi.crescer.api.mapper.FriendshipRequestMapper.toResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListFriendshipServiceTest {

    @InjectMocks
    private ListFriendshipService tested;

    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Mock
    private FriendshipRepository friendshipRepository;

    @Mock
    private FriendRequestRepository friendRequestRepository;


    @Test
    @DisplayName("Deve retornar uma Page vazia de amigos do usuario logado se o usuario não possuir amigos")
    void deveRetornarUmaPageVaziaDeAmigosDoUsuarioLogadoSeOUsuarioNaoPossuirAmigos(){
        Usuario usuario = UsuarioFactory.get();
        Pageable pageable = PageRequest.of(0,5);

        when(usuarioAutenticadoService.getId()).thenReturn(usuario.getId());

        Page<Friendship> friendshipPage = new PageImpl<>(new ArrayList<>());

        when(friendshipRepository.findByUsuarioIdOrderByFriendNameAsc(usuario.getId(), pageable)).thenReturn(friendshipPage);
        Page<FriendshipResponse> friendships = tested.get(pageable);

        assertEquals(friendships.getTotalElements(), friendshipPage.getTotalElements());
    }

    @Test
    @DisplayName("Deve retornar uma Page com os de amigos do usuario logado")
    void deveRetornarUmaPageComOsDeAmigosDoUsuarioLogado(){
        Usuario usuario = UsuarioFactory.get();
        Pageable pageable = PageRequest.of(0,5);

        when(usuarioAutenticadoService.getId()).thenReturn(usuario.getId());

        List<Friendship> friendshipList = new ArrayList<>();
        friendshipList.add(FriendshipFactory.get());
        friendshipList.add(FriendshipFactory.get());

        Page<Friendship> friendshipPage = new PageImpl<>(friendshipList);

        when(friendshipRepository.findByUsuarioIdOrderByFriendNameAsc(usuario.getId(), pageable)).thenReturn(friendshipPage);
        Page<FriendshipResponse> friendships = tested.get(pageable);

        assertEquals(friendships.getTotalElements(), friendshipPage.getTotalElements());
    }

    @Test
    @DisplayName("Deve receber lista de requisiçoes de amizade enviadas ao usuario")
    void deveReceberListaDeRequisicoesDeAmizadeEnviadasAoUsuario() {

        Usuario usuario = UsuarioFactory.get();

        FriendRequest request = FriendRequest.builder()
                .id(getRandomLong())
                .usuarioSender(UsuarioFactory.get())
                .usuarioReceiver(usuario)
                .build();

        List<FriendRequest> requestList = new ArrayList<>();
        requestList.add(request);

        List<FriendshipResponse> requestToResponse= requestList.stream()
                        .map(element -> toResponse(element.getUsuarioSender()))
                                .collect(Collectors.toList());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(friendRequestRepository.findByUsuarioReceiver(usuario)).thenReturn(requestList);

        List<FriendshipResponse> response = tested.getRequests();

        verify(usuarioAutenticadoService).get();
        verify(friendRequestRepository).findByUsuarioReceiver(usuario);

        assertEquals(requestToResponse.size(), response.size());
    }

    @Test
    @DisplayName("Deve receber lista vazia caso não tenham requisições")
    void deveReceberListaVaziaCasoNaoTenhamRequisicoes() {

        Usuario usuario = UsuarioFactory.get();

        List<FriendRequest> request = new ArrayList<>();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(friendRequestRepository.findByUsuarioReceiver(usuario)).thenReturn(request);

        List<FriendshipResponse> response = tested.getRequests();

        verify(usuarioAutenticadoService).get();
        verify(friendRequestRepository).findByUsuarioReceiver(usuario);

        assertEquals(0, response.size());
    }

    @Test
    @DisplayName("Deve receber lista de requisições de amizade do usuário logado")
    void deveReceberListaDeRequisicoesDeAmizadeDoUsuarioLogado() {

        Usuario usuario = UsuarioFactory.get();

        FriendRequest request = FriendRequest.builder()
                .id(getRandomLong())
                .usuarioSender(usuario)
                .usuarioReceiver(UsuarioFactory.get())
                .build();

        List<FriendRequest> requestList = new ArrayList<>();
        requestList.add(request);

        List<FriendshipResponse> requestToResponse= requestList.stream()
                .map(element -> toResponse(element.getUsuarioReceiver()))
                .collect(Collectors.toList());

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(friendRequestRepository.findByUsuarioSender(usuario)).thenReturn(requestList);

        List<FriendshipResponse> response = tested.getMyRequests();

        verify(usuarioAutenticadoService).get();
        verify(friendRequestRepository).findByUsuarioSender(usuario);

        assertEquals(requestToResponse.size(), response.size());
    }

    @Test
    @DisplayName("Deve receber lista vazia caso não tenham requisições do usuário logado")
    void deveReceberListaVaziaCasoNaoTenhamRequisicoesDoUsuarioLogado() {

        Usuario usuario = UsuarioFactory.get();

        List<FriendRequest> request = new ArrayList<>();

        when(usuarioAutenticadoService.get()).thenReturn(usuario);
        when(friendRequestRepository.findByUsuarioSender(usuario)).thenReturn(request);

        List<FriendshipResponse> response = tested.getMyRequests();

        verify(usuarioAutenticadoService).get();
        verify(friendRequestRepository).findByUsuarioSender(usuario);

        assertEquals(0, response.size());
    }
}

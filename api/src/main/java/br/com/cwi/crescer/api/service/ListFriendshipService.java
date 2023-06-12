package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendRequestRepository;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.api.mapper.FriendshipRequestMapper.toResponse;

@Service
public class ListFriendshipService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    public Page<FriendshipResponse> get(Pageable pageable) {
        Long usuarioId = usuarioAutenticadoService.getId();

        return friendshipRepository.findByUsuarioIdOrderByFriendNameAsc(usuarioId, pageable)
                .map(FriendshipMapper::toResponse);
    }

    public List<FriendshipResponse> getMyRequests() {
        Usuario usuario = usuarioAutenticadoService.get();

        return friendRequestRepository.findByUsuarioSender(usuario).stream()
                .map(request -> toResponse(request.getUsuarioReceiver()))
                .collect(Collectors.toList());
    }

    public List<FriendshipResponse> getRequests() {
        Usuario usuario = usuarioAutenticadoService.get();

        return friendRequestRepository.findByUsuarioReceiver(usuario).stream()
                .map(request -> toResponse(request.getUsuarioSender()))
                .collect(Collectors.toList());
    }
}

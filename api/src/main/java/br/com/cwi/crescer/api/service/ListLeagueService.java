package br.com.cwi.crescer.api.service;

import br.com.cwi.crescer.api.controller.response.FriendshipResponse;
import br.com.cwi.crescer.api.domain.Friendship;
import br.com.cwi.crescer.api.mapper.FriendshipMapper;
import br.com.cwi.crescer.api.repository.FriendshipRepository;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListLeagueService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<FriendshipResponse> list(Pageable pageable) {

        Long usuarioId = usuarioAutenticadoService.getId();

        List<Long> ids = friendshipRepository.findAllFriendsIdsByUsuarioId(usuarioId)
                .stream()
                .map(Friendship::getFriend)
                .map(Usuario::getId)
                .collect(Collectors.toList());

        ids.add(usuarioId);

        return usuarioRepository.findByIdInOrderByLeaguePointsDesc(ids, pageable)
                .map(FriendshipMapper::toEntity);
    }
}

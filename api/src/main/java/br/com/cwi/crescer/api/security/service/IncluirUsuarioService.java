package br.com.cwi.crescer.api.security.service;

import br.com.cwi.crescer.api.domain.Course;
import br.com.cwi.crescer.api.domain.Icon;
import br.com.cwi.crescer.api.security.controller.request.UsuarioRequest;
import br.com.cwi.crescer.api.security.controller.response.UsuarioResponse;
import br.com.cwi.crescer.api.security.domain.Permissao;
import br.com.cwi.crescer.api.security.domain.Usuario;
import br.com.cwi.crescer.api.security.repository.UsuarioRepository;
import br.com.cwi.crescer.api.security.service.validations.ValidateUniqueEmailService;
import br.com.cwi.crescer.api.service.SearchCourseService;
import br.com.cwi.crescer.api.service.SearchIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

import static br.com.cwi.crescer.api.domain.enums.LeagueTitle.GASTADOR;
import static br.com.cwi.crescer.api.security.mapper.UsuarioMapper.toEntity;
import static br.com.cwi.crescer.api.security.mapper.UsuarioMapper.toResponse;
import static java.time.LocalDate.now;


@Service
public class IncluirUsuarioService {

    @Autowired
    private ValidateUniqueEmailService validateUniqueEmailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SearchIconService searchIconService;

    @Autowired
    private SearchCourseService searchCourseService;

    @Transactional
    public UsuarioResponse incluir(UsuarioRequest request) {

        validateUniqueEmailService.validate(request.getEmail());

        Usuario usuario = toEntity(request);
        Icon icon = searchIconService.byId(1L);
        Course course = searchCourseService.byId(1L);

        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setActive(true);
        usuario.setCurrentIcon(icon);
        usuario.setCreateIn(now());
        usuario.setUpdateIn(now());
        usuario.setLeaguePoints(0L);
        usuario.setLeagueTitle(GASTADOR);
        usuario.setCoins(0L);
        usuario.setXp(0L);

        usuario.addIcon(icon);
        usuario.addCourse(course);

        if(request.getPermissions() == null) {
            request.setPermissions(new ArrayList<>());
        }
        request.getPermissions().add("USER");

        Optional.ofNullable(request.getPermissions())
                .ifPresent(permissions ->
                        permissions.forEach(permission ->
                                usuario.addPermission(Permissao.builder().name(permission).build())));

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }
}

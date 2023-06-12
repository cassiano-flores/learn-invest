package br.com.cwi.crescer.api.security.controller.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioRequest {

    @NotBlank
    private String name;

    private String nickname;

    @NotNull @Email
    private String email;

    @NotBlank
    private String password;

    private List<String> permissions;
}

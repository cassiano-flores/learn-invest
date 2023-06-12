package br.com.cwi.crescer.api.security.controller.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank
    private String password;
}

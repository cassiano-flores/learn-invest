package br.com.cwi.crescer.api.security.controller.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ForgotPasswordRequest {

    @NotBlank @Email
    private String email;
}

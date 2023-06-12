package br.com.cwi.crescer.api.controller.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProfileEditedRequest {

    @NotBlank
    private String name;

    private String nickname;

    @NotNull
    private Long iconId;
}

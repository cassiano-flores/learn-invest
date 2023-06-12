package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileEditedResponse {

    private String name;
    private String nickname;
    private IconResponse currentIcon;
}

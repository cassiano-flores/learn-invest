package br.com.cwi.crescer.api.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IconResponse {

    private Long id;
    private String name;
    private Long price;
}

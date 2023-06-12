package br.com.cwi.crescer.api.mapper;

import br.com.cwi.crescer.api.controller.response.IconResponse;
import br.com.cwi.crescer.api.domain.Icon;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IconMapper {

    public static IconResponse toResponse(Icon icon) {

        return IconResponse.builder()
                .id(icon.getId())
                .name(icon.getName())
                .price(icon.getPrice())
                .build();
    }
}

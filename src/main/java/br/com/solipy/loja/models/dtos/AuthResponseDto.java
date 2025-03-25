package br.com.solipy.loja.models.dtos;

import lombok.Builder;

@Builder
public record AuthResponseDto(
        String token
) {
}

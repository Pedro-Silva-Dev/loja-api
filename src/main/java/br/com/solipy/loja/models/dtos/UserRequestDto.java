package br.com.solipy.loja.models.dtos;

import lombok.Builder;

@Builder(setterPrefix = "set")
public record UserRequestDto(
        Long id,
        String name,
        String username
) {
}
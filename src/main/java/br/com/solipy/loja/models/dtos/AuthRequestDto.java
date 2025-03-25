package br.com.solipy.loja.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AuthRequestDto(
        @NotNull @NotEmpty String username,
        @NotNull @NotEmpty String password
) {
}

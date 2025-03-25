package br.com.solipy.loja.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record RegisterUserDto(
        @NotNull @NotEmpty String name,
        @NotNull @NotEmpty @Email String email,
        @NotNull @NotEmpty @Length(min = 8) String password,
        @NotNull @NotEmpty String phone
) {
}

package com.ecommerce.api.infrastructure.adapters.in.rest.dto.request;
}
    private String fullName;
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    @NotBlank(message = "Nome completo é obrigatório")

    private String password;
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @NotBlank(message = "Senha é obrigatória")

    private String email;
    @Email(message = "Email deve ser válido")
    @NotBlank(message = "Email é obrigatório")

    private String username;
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres")
    @NotBlank(message = "Username é obrigatório")

public class RegisterRequest {
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;



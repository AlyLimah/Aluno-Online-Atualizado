package br.com.alunoonline.api.infra.security;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank(message = "Login é obrigatório.")
        String login,

        @NotBlank(message = "Senha é obrigatória")
        String senha

) {

}

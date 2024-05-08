package com.attus.attusvaga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRecordDto(
        @NotBlank(message = "Logradouro é obrigatório")
        String street,
        @NotNull(message = "CEP é obrigatório.")
        Integer cep,
        @NotNull(message = "Número é obrigatório.")
        Integer number,
        @NotBlank(message = "Cidade é obrigatório.")
        String city,
        @NotNull(message = "Estado é obrigatório.")
        String state,
        @NotNull(message = "Campo obrigatório.")
        Long pessoa,

        boolean favorito
) {
}

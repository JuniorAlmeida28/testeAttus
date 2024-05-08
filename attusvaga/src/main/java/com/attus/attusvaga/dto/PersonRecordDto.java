package com.attus.attusvaga.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record PersonRecordDto(
        @NotBlank(message = "O nome não pode ser vazio")
        String fullName,
        @NotNull(message = "Data de nascimento inválida")
        LocalDate birthDate
) {
}

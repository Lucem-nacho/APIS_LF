package com.ms_auth.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @Schema(description = "Token JWT de acceso", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnRlQGVtYWlsLmNvbSIsImlhdCI6MTYxNjQ...")
    private String token;
}
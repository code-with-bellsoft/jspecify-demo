package dev.cyberjar.jspecify.api;

import jakarta.validation.constraints.NotBlank;

public record RegisterNodeRequest(
        @NotBlank String codename,
        @NotBlank String district,
        String alias,
        String operator
) {
}

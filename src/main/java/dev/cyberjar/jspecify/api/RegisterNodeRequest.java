package dev.cyberjar.jspecify.api;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.Nullable;

public record RegisterNodeRequest(
        @NotBlank String codename,
        @NotBlank String district,
        @Nullable String alias,
        @Nullable String operator
) {
}

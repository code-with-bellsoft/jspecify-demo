package dev.cyberjar.jspecify.domain;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public record Signal<T extends @Nullable Object>(
        String channel,
        T payload
) {
    public @NonNull T requirePayload() {
        return Objects.requireNonNull(payload, "Signal payload is missing");
    }
}
package dev.cyberjar.jspecify.domain;

public record Signal<T>(
        String channel,
        T payload
) {
}

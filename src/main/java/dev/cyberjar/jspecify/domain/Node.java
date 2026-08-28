package dev.cyberjar.jspecify.domain;

import org.jspecify.annotations.Nullable;

import java.time.Instant;

public record Node(
        long id,
        String codename,
        String district,
        @Nullable String alias,
        @Nullable  String operator,
        NodeStatus status,
        Instant lastSeen,
        @Nullable Instant decommissionedAt
) {
}

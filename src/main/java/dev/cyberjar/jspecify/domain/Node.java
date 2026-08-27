package dev.cyberjar.jspecify.domain;

import java.time.Instant;

public record Node(
        long id,
        String codename,
        String district,
        String alias,
        String operator,
        NodeStatus status,
        Instant lastSeen,
        Instant decommissionedAt
) {
}

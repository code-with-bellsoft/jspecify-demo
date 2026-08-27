package dev.cyberjar.jspecify.api;

import dev.cyberjar.jspecify.domain.Node;

import java.time.Instant;

public record NodeResponse(
        long id,
        String codename,
        String district,
        String alias,
        String operator,
        String status,
        Instant lastSeen,
        Instant decommissionedAt
) {

    public static NodeResponse from(Node node) {
        return new NodeResponse(
                node.id(),
                node.codename(),
                node.district(),
                node.alias(),
                node.operator(),
                node.status().name(),
                node.lastSeen(),
                node.decommissionedAt()
        );
    }
}

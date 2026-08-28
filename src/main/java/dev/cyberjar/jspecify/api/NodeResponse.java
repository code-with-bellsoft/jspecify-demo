package dev.cyberjar.jspecify.api;

import dev.cyberjar.jspecify.domain.Node;
import org.jspecify.annotations.Nullable;

import java.time.Instant;

public record NodeResponse(
        long id,
        String codename,
        String district,
        @Nullable String alias,
        @Nullable String operator,
        String status,
        Instant lastSeen,
        @Nullable Instant decommissionedAt
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

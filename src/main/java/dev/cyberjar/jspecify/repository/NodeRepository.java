package dev.cyberjar.jspecify.repository;


import dev.cyberjar.jspecify.domain.Node;
import dev.cyberjar.jspecify.domain.NodeStatus;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Repository
public class NodeRepository {

    private final JdbcClient jdbc;

    public NodeRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public @Nullable Node findById(long id) {
        return jdbc.sql("""
                        SELECT id, codename, district, alias, operator_name,
                               status, last_seen, decommissioned_at
                        FROM node
                        WHERE id = :id
                        """)
                .param("id", id)
                .query(this::mapNode)
                .optional()
                .orElse(null);
    }

    public List<Node> findAll() {
        return jdbc.sql("""
                        SELECT id, codename, district, alias, operator_name,
                               status, last_seen, decommissioned_at
                        FROM node
                        ORDER BY id
                        """)
                .query(this::mapNode)
                .list();
    }

    public List<Node> findByDistrict(String district) {
        return jdbc.sql("""
                        SELECT id, codename, district, alias, operator_name,
                               status, last_seen, decommissioned_at
                        FROM node
                        WHERE LOWER(district) = LOWER(:district)
                        ORDER BY id
                        """)
                .param("district", district)
                .query(this::mapNode)
                .list();
    }

    public Node insert(
            String codename,
            String district,
            @Nullable String alias,
            @Nullable String operator
    ) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.sql("""
            INSERT INTO node (
                codename,
                district,
                alias,
                operator_name,
                status,
                last_seen,
                decommissioned_at
            )
            VALUES (
                :codename,
                :district,
                :alias,
                :operator,
                'ACTIVE',
                CURRENT_TIMESTAMP,
                NULL
            )
            """)
                .param("codename", codename)
                .param("district", district)
                .param("alias", alias)
                .param("operator", operator)
                .update(keyHolder, "id");

        Number generatedId = Objects.requireNonNull(
                keyHolder.getKey(),
                "Database did not return a generated node id"
        );

        return Objects.requireNonNull(
                findById(generatedId.longValue()),
                "Inserted node could not be read back"
        );
    }

    private Node mapNode(ResultSet rs, int rowNum) throws SQLException {
        return new Node(
                rs.getLong("id"),
                requiredString(rs, "codename"),
                requiredString(rs, "district"),
                rs.getString("alias"),
                rs.getString("operator_name"),
                NodeStatus.valueOf(requiredString(rs, "status")),
                requiredTimestamp(rs, "last_seen").toInstant(),
                nullableInstant(rs.getTimestamp("decommissioned_at"))
        );
    }

    private String requiredString(ResultSet rs, String column) throws SQLException {
        return Objects.requireNonNull(
                rs.getString(column),
                () -> "NULL in required column: " + column
        );
    }

    private Timestamp requiredTimestamp(ResultSet rs, String column) throws SQLException {
        return Objects.requireNonNull(
                rs.getTimestamp(column),
                () -> "NULL in required column: " + column
        );
    }

    private @Nullable Instant nullableInstant(@Nullable Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toInstant();
    }
}

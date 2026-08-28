package dev.cyberjar.jspecify.service;

import dev.cyberjar.jspecify.domain.Node;
import dev.cyberjar.jspecify.domain.Signal;
import dev.cyberjar.jspecify.repository.NodeRepository;
import org.springframework.stereotype.Service;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Service
public class NodeService {

    private final NodeRepository repository;

    public NodeService(NodeRepository repository) {
        this.repository = repository;
    }

    public @Nullable Node find(long id) {
        return repository.findById(id);
    }

    public List<Node> search(@Nullable String district) {
        if (district == null || district.isBlank()) {
            return repository.findAll();
        }

        return repository.findByDistrict(district);
    }

    public Node register(
            String codename,
            String district,
            @Nullable String alias,
            @Nullable String operator
    ) {
        return repository.insert(
                codename,
                district,
                alias,
                operator
        );
    }

    public String displayName(Node node) {
        if (node.alias() != null) {
            return node.alias();
        }

        return node.codename();
    }

    public List<Signal<@Nullable String>> signals(Node node) {
        return List.of(
                new Signal<>("public-alias", node.alias()),
                new Signal<>("operator", node.operator()),
                new Signal<>("status", node.status().name())
        );
    }
}

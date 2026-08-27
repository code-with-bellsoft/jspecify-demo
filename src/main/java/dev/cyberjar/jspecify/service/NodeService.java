package dev.cyberjar.jspecify.service;

import dev.cyberjar.jspecify.domain.NewNode;
import dev.cyberjar.jspecify.domain.Node;
import dev.cyberjar.jspecify.domain.Signal;
import dev.cyberjar.jspecify.repository.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {

    private final NodeRepository repository;

    public NodeService(NodeRepository repository) {
        this.repository = repository;
    }

    public Node find(long id) {
        return repository.findById(id);
    }

    public List<Node> search(String district) {
        if (district == null || district.isBlank()) {
            return repository.findAll();
        }

        return repository.findByDistrict(district);
    }

    public Node register(NewNode node) {
        return repository.insert(node);
    }

    public String displayName(Node node) {
        if (node.alias() != null) {
            return node.alias();
        }

        return node.codename();
    }

    public List<Signal<String>> signals(Node node) {
        return List.of(
                new Signal<>("public-alias", node.alias()),
                new Signal<>("operator", node.operator()),
                new Signal<>("status", node.status().name())
        );
    }
}

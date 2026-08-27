package dev.cyberjar.jspecify.api;

import dev.cyberjar.jspecify.domain.NewNode;
import dev.cyberjar.jspecify.domain.Node;
import dev.cyberjar.jspecify.domain.Signal;
import dev.cyberjar.jspecify.service.NodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    private final NodeService service;

    public NodeController(NodeService service) {
        this.service = service;
    }

    @GetMapping
    public List<NodeResponse> nodes(
            @RequestParam(required = false) String district) {

        return service.search(district)
                .stream()
                .map(NodeResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public NodeResponse node(@PathVariable long id) {
        Node node = service.find(id);

        // Deliberate baseline bug for the JSpecify demo:
        // find(id) can return null, but plain Java does not express that contract.
        return NodeResponse.from(node);
    }

    @GetMapping("/{id}/signals")
    public List<Signal<String>> signals(@PathVariable long id) {
        Node node = service.find(id);

        if (node == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Node not found"
            );
        }

        return service.signals(node);
    }

    @PostMapping
    public ResponseEntity<NodeResponse> register(
            @Valid @RequestBody RegisterNodeRequest request) {

        Node created = service.register(
                new NewNode(
                        request.codename(),
                        request.district(),
                        request.alias(),
                        request.operator()
                )
        );

        return ResponseEntity
                .created(URI.create("/api/nodes/" + created.id()))
                .body(NodeResponse.from(created));
    }
}

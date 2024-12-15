package Graph.GraphTraversing;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}


@RestController
@RequestMapping("/graph")
class GraphController {

    private final GraphService graphService = new GraphService();

    @PostMapping("/nodes")
    public String createNodes(@RequestBody List<Node> nodes) {
        for (Node node : nodes) {
            graphService.addNode(node);
        }
        return "Nodes added successfully";
    }

    @GetMapping("/nodes/{id}")
    public Node getNode(@PathVariable String id) {
        return graphService.getNode(id);
    }

    @PostMapping("/relationships")
    public String addRelationship(@RequestBody List<Relationship> relationships) {
        for(Relationship r:relationships){
            return graphService.addRelationship(r);
        }
        return "Relation ships added successfully";
    }

    @GetMapping("/paths")
    public List<String> findPath(@RequestParam String startNodeId, @RequestParam String endNodeId) {
        return graphService.findPath(startNodeId, endNodeId);
    }

    @GetMapping("/nodes/{id}/depth")
    public int calculateDepth(@PathVariable String id) {
        return graphService.calculateDepth(id);
    }

    @GetMapping("/common-ancestor")
    public String findCommonAncestor(@RequestParam String nodeId1, @RequestParam String nodeId2) {
        return graphService.findCommonAncestor(nodeId1, nodeId2);
    }
}


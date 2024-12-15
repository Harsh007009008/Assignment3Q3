package Graph.GraphTraversing;

import java.util.*;

class GraphService {
    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<String, List<String>> edges = new HashMap<>();

    public String addNode(Node node) {
        if (nodes.containsKey(node.getId())) {
            throw new IllegalArgumentException("Node with ID " + node.getId() + " already exists.");
        }
        nodes.put(node.getId(), node);
        edges.putIfAbsent(node.getId(), new ArrayList<>());
        if (node.getParentId() != null) {
            addRelationship(new Relationship(node.getParentId(), node.getId()));
        }
        return "Node added successfully";
    }

    public Node getNode(String id) {
        Node node = nodes.get(id);
        if (node == null) {
            throw new IllegalArgumentException("Node with ID " + id + " not found.");
        }
        return node;
    }

    public String addRelationship(Relationship relationship) {
        String parentId = relationship.getParentId();
        String childId = relationship.getChildId();

        if (!nodes.containsKey(parentId) || !nodes.containsKey(childId)) {
            throw new IllegalArgumentException("Invalid parent or child ID.");
        }
        edges.get(parentId).add(childId);
        return "Relationship added successfully";
    }

    public List<String> findPath(String startNodeId, String endNodeId) {
        System.out.println("Finding path from " + startNodeId + " to " + endNodeId);
        List<String> path = new ArrayList<>();
        if (dfs(startNodeId, endNodeId, path, new HashSet<>())) {
            System.out.println("Path found: " + path);
            return path;
        }
        throw new IllegalArgumentException("No path found between " + startNodeId + " and " + endNodeId);
    }

    private boolean dfs(String current, String target, List<String> path, Set<String> visited) {
        System.out.println("Visiting node: " + current);
        if (visited.contains(current)) {
            System.out.println("Already visited: " + current);
            return false;
        }

        visited.add(current);
        path.add(current);

        if (current.equals(target)) {
            System.out.println("Target node reached: " + current);
            return true;
        }

        // Iterate through all children of the current node
        List<String> children = edges.getOrDefault(current, Collections.emptyList());
        System.out.println("Children of " + current + ": " + children);

        for (String child : children) {
            if (dfs(child, target, path, visited)) {
                return true;
            }
        }

        // Backtrack if the path to the target was not found
        System.out.println("Backtracking from: " + current);
        path.remove(path.size() - 1);
        return false;
    }


    public int calculateDepth(String id) {
        int depth = 0;
        String current = id;

        while (nodes.containsKey(current) && nodes.get(current).getParentId() != null) {
            current = nodes.get(current).getParentId();
            depth++;
        }

        return depth;
    }

    public String findCommonAncestor(String nodeId1, String nodeId2) {
        Set<String> ancestors1 = findAncestors(nodeId1);
        Set<String> ancestors2 = findAncestors(nodeId2);

        for (String ancestor : ancestors1) {
            if (ancestors2.contains(ancestor)) {
                return ancestor;
            }
        }

        throw new IllegalArgumentException("No common ancestor found.");
    }

    private Set<String> findAncestors(String nodeId) {
        Set<String> ancestors = new LinkedHashSet<>();
        String current = nodeId;

        while (nodes.containsKey(current) && nodes.get(current).getParentId() != null) {
            current = nodes.get(current).getParentId();
            ancestors.add(current);
        }

        return ancestors;
    }
}

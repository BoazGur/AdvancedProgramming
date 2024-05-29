package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Node {
    private String name;
    private List<Node> edges;
    private Message message;

    public Node(String name) {
        this.name = name;
        this.edges = new ArrayList<Node>();
    }

    public String getName() {
        return name;
    }

    public List<Node> getEdges() {
        return new ArrayList<Node>(edges);
    }

    public Message getMessage() {
        return message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEdges(List<Node> edges) {
        this.edges = new ArrayList<Node>(edges);
    }

    public void addEdge(Node edge) {
        edges.add(edge);
    }

    public boolean hasCycles() {
        HashSet<Node> visited = new HashSet<Node>();
        HashSet<Node> exploring = new HashSet<Node>();

        return hasCycles(visited, exploring);
    }

    private boolean hasCycles(HashSet<Node> visited, HashSet<Node> exploring) {
        if (visited.contains(this)) // cycle detected
            return true;

        visited.add(this); // visited node
        exploring.add(this); // exploring this node

        for (Node neighbor : edges)
            if (!visited.contains(neighbor)) {
                if (neighbor.hasCycles(visited, exploring))
                    return true; // recursive call
            } else if (exploring.contains(neighbor)) {
                return true;
            }
        
        exploring.remove(this);
        return false; // no cycles detected
    }
}

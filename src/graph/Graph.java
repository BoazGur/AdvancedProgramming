package graph;

import java.util.ArrayList;

import graph.TopicManagerSingleton.TopicManager;

public class Graph extends ArrayList<Node> {
    public boolean hasCycles() {
        for (Node node : this)
            if (node.hasCycles())
                return true;

        return false;
    } 

    public void createFromTopics() {
        TopicManager topicManager = TopicManagerSingleton.get();

        for (Topic topic : topicManager.getTopics()) {
            Node topicNode = new Node("T" + topic.name);
            this.add(topicNode);

            for (Agent agent : topic.getSubs()) {
                Node agentNode = getNode(agent);
                topicNode.addEdge(agentNode);
            }
        
            for (Agent agent : topic.getPubs()) {
                Node agentNode = getNode(agent);
                agentNode.addEdge(topicNode);
            }
        }
    }

    private Node getNode(Agent agent) {
        for (Node node : this) 
            if (node.getName().equals("A" + agent.getName()))
                return node;
        
        Node agentNode = new Node("A" + agent.getName());
        this.add(agentNode);
        return agentNode;
    }
}

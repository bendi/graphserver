package org.collibra.challenge.protocol.commands;

public class AddEdgeRequest implements Request {

    private final String startNode;

    private final String endNode;

    private final int weight;

    /**
     *
     * @param startNode
     * @param endNode
     * @param weight
     */
    public AddEdgeRequest(String startNode, String endNode, int weight) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
    }

    public String getStartNode() {
        return startNode;
    }

    public String getEndNode() {
        return endNode;
    }

    public int getWeight() {
        return weight;
    }
}

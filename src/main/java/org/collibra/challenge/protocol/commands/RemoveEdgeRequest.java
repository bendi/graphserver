package org.collibra.challenge.protocol.commands;

public class RemoveEdgeRequest implements WriteRequest {

    private final String startNode;

    private final String endNode;

    /**
     * @param startNode
     * @param endNode
     */
    public RemoveEdgeRequest(String startNode, String endNode)
    {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public String getStartNode()
    {
        return startNode;
    }

    public String getEndNode()
    {
        return endNode;
    }
}

package org.collibra.challenge.protocol.commands;

public class ShortestPathRequest implements ReadRequest {

    private final String fromNode;

    private final String toNode;

    /**
     *
     * @param fromNode
     * @param toNode
     */
    public ShortestPathRequest(String fromNode, String toNode)
    {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    public String getFromNode()
    {
        return fromNode;
    }

    public String getToNode()
    {
        return toNode;
    }
}

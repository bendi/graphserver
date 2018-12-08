package org.collibra.challenge.protocol.commands;

public class CloserThanRequest implements ReadRequest {

    private final String nodeName;

    private final Integer weight;

    public CloserThanRequest(String nodeName, Integer weight)
    {
        this.nodeName = nodeName;
        this.weight = weight;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    public Integer getWeight()
    {
        return weight;
    }
}

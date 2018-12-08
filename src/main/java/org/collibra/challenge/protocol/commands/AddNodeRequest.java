package org.collibra.challenge.protocol.commands;

public class AddNodeRequest implements WriteRequest {

    private final String nodeName;

    /**
     * @param nodeName
     */
    public AddNodeRequest(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getNodeName()
    {
        return nodeName;
    }
}

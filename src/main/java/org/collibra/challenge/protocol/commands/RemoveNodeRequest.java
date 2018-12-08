package org.collibra.challenge.protocol.commands;

public class RemoveNodeRequest implements Request {

    private final String nodeName;

    /**
     *
     * @param nodeName
     */
    public RemoveNodeRequest(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }
}

package org.collibra.challenge.protocol.commands;

public class NodeOperationResponse implements Response {

    private final boolean removed;

    /**
     * @param removed
     */
    public NodeOperationResponse(boolean removed)
    {
        this.removed = removed;
    }

    /**
     *
     */
    public NodeOperationResponse()
    {
        this.removed = false;
    }

    @Override
    public String toResponseString()
    {
        if ( removed ) {
            return "NODE REMOVED";
        }
        else {
            return "NODE ADDED";
        }
    }
}

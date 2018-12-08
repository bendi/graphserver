package org.collibra.challenge.protocol.commands;

public class EdgeOperationResponse implements Response {

    private final boolean removed;

    /**
     * @param removed
     */
    public EdgeOperationResponse(boolean removed)
    {
        this.removed = removed;
    }

    /**
     *
     */
    public EdgeOperationResponse()
    {
        this.removed = false;
    }

    @Override
    public String toResponseString()
    {
        if ( removed ) {
            return "EDGE REMOVED";
        }
        else {
            return "EDGE ADDED";
        }
    }
}

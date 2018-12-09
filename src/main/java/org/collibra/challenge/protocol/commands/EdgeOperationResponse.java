package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

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

    public boolean isRemoved()
    {
        return removed;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }

}

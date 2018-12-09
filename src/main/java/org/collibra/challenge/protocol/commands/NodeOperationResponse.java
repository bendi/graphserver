package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

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

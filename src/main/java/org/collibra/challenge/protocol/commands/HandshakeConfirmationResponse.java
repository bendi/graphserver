package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class HandshakeConfirmationResponse implements Response {

    private final String name;

    /**
     * @param name
     */
    public HandshakeConfirmationResponse(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }
}

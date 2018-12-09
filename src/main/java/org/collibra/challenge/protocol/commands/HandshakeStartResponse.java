package org.collibra.challenge.protocol.commands;

import java.util.UUID;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class HandshakeStartResponse implements Response {

    private final UUID sessionId;

    public HandshakeStartResponse(UUID sessionId)
    {
        this.sessionId = sessionId;
    }

    public UUID getSessionId()
    {
        return sessionId;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }

}

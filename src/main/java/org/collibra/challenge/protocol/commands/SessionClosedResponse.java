package org.collibra.challenge.protocol.commands;

import java.time.Duration;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class SessionClosedResponse implements Response {

    private final String name;

    private final Duration sessionDuration;

    /**
     * @param name
     * @param sessionDuration
     */
    public SessionClosedResponse(String name, Duration sessionDuration)
    {
        this.name = name;
        this.sessionDuration = sessionDuration;
    }

    public String getName()
    {
        return name;
    }

    public Duration getSessionDuration()
    {
        return sessionDuration;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }

}

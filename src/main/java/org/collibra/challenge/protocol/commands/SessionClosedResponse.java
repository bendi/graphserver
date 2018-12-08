package org.collibra.challenge.protocol.commands;

import java.time.Duration;

public class SessionClosedResponse implements Response {

    private final static String COMMAND_PATTERN = "BYE %s, WE SPOKE FOR %d MS";

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
    public String toResponseString()
    {
        return String.format( COMMAND_PATTERN, name == null ? "" : name, sessionDuration.toMillis() );
    }
}

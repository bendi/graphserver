package org.collibra.challenge.protocol.commands;

import java.util.UUID;

public class HandshakeStartResponse implements Response {

    private static final String COMMAND_TEMPLATE = "HI, I'M %s";

    private final UUID sessionId;

    public HandshakeStartResponse(UUID sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toResponseString() {
        return String.format(COMMAND_TEMPLATE, sessionId.toString());
    }
}

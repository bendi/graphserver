package org.collibra.challenge.protocol.commands;

public class HandshakeConfirmationResponse implements Response {

    private final String COMMAND_TEMPLATE = "HI %s";

    private final String name;

    /**
     * @param name
     */
    public HandshakeConfirmationResponse(String name) {
        this.name = name;
    }

    @Override
    public String toResponseString() {
        return String.format(COMMAND_TEMPLATE, name);
    }

}

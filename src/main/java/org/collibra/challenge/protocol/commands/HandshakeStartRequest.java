package org.collibra.challenge.protocol.commands;

public class HandshakeStartRequest implements Request {

    private final String name;


    public HandshakeStartRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

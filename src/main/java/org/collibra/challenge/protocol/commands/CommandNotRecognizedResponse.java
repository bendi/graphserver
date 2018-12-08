package org.collibra.challenge.protocol.commands;

public class CommandNotRecognizedResponse implements Response {

    @Override
    public String toResponseString()
    {
        return "SORRY, I DIDN'T UNDERSTAND THAT";
    }
}

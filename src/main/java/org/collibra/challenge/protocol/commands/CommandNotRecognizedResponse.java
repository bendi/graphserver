package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class CommandNotRecognizedResponse implements Response {

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }

}

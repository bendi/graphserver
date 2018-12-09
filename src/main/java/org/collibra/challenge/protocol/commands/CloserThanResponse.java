package org.collibra.challenge.protocol.commands;

import java.util.List;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class CloserThanResponse implements Response {

    private final List<String> paths;

    /**
     * @param paths
     */
    public CloserThanResponse(List<String> paths)
    {
        this.paths = paths;
    }

    public List<String> getPaths()
    {
        return paths;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }

}

package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class ShortestPathResponse implements Response {

    private final int shortesPathWeightsSum;

    /**
     * @param shortesPathWeightsSum
     */
    public ShortestPathResponse(int shortesPathWeightsSum)
    {
        this.shortesPathWeightsSum = shortesPathWeightsSum;
    }

    public int getShortesPathWeightsSum()
    {
        return shortesPathWeightsSum;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }
}

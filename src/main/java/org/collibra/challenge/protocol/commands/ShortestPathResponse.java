package org.collibra.challenge.protocol.commands;

public class ShortestPathResponse implements Response {

    private final int shortesPathWeightsSum;

    /**
     * @param shortesPathWeightsSum
     */
    public ShortestPathResponse(int shortesPathWeightsSum)
    {
        this.shortesPathWeightsSum = shortesPathWeightsSum;
    }

    @Override
    public String toResponseString()
    {
        return String.valueOf( shortesPathWeightsSum );
    }
}

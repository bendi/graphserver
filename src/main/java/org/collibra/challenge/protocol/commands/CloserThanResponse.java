package org.collibra.challenge.protocol.commands;

import java.util.List;

public class CloserThanResponse implements Response {
    private final List<String> paths;

    /**
     * @param paths
     */
    public CloserThanResponse(List<String> paths)
    {
        this.paths = paths;
    }

    @Override
    public String toResponseString()
    {
        return paths.toString();
    }
}

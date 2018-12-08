package org.collibra.challenge.protocol.commands;

import java.util.List;
import java.util.stream.Collectors;

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
        return paths.stream().collect( Collectors.joining(","));
    }
}

package org.collibra.challenge.protocol;

import org.collibra.challenge.protocol.commands.Request;
import org.collibra.challenge.protocol.exception.UnsupportedCommandException;

public interface ProtocolParser {

    static ProtocolParser getDefaultProtocolParser()
    {
        return new DefaultProtocolParser();
    }

    /**
     * @param commandBytes
     * @return
     * @throws UnsupportedCommandException
     */
    Request parse(byte[] commandBytes) throws UnsupportedCommandException;
}

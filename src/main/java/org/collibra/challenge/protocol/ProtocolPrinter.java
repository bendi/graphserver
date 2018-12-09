package org.collibra.challenge.protocol;

import org.collibra.challenge.protocol.commands.CloserThanResponse;
import org.collibra.challenge.protocol.commands.CommandNotRecognizedResponse;
import org.collibra.challenge.protocol.commands.EdgeOperationResponse;
import org.collibra.challenge.protocol.commands.HandshakeConfirmationResponse;
import org.collibra.challenge.protocol.commands.HandshakeStartResponse;
import org.collibra.challenge.protocol.commands.NodeOperationErrorResponse;
import org.collibra.challenge.protocol.commands.NodeOperationResponse;
import org.collibra.challenge.protocol.commands.SessionClosedResponse;
import org.collibra.challenge.protocol.commands.ShortestPathResponse;

public interface ProtocolPrinter {

    /**
     * @return
     */
    static ProtocolPrinter getDefaultProtocolPrinter()
    {
        return new DefaultProtocolPrinter();
    }

    /**
     * @param closerThanResponse
     * @return
     */
    byte[] print(CloserThanResponse closerThanResponse);

    /**
     * @param commandNotRecognizedResponse
     * @return
     */
    byte[] print(CommandNotRecognizedResponse commandNotRecognizedResponse);

    /**
     * @param edgeOperationResponse
     * @return
     */
    byte[] print(EdgeOperationResponse edgeOperationResponse);

    /**
     * @param handshakeStartResponse
     * @return
     */
    byte[] print(HandshakeStartResponse handshakeStartResponse);

    /**
     * @param nodeOperationErrorResponse
     * @return
     */
    byte[] print(NodeOperationErrorResponse nodeOperationErrorResponse);

    /**
     * @param nodeOperationResponse
     * @return
     */
    byte[] print(NodeOperationResponse nodeOperationResponse);

    /**
     * @param sessionClosedResponse
     * @return
     */
    byte[] print(SessionClosedResponse sessionClosedResponse);

    /**
     * @param shortestPathResponse
     * @return
     */
    byte[] print(ShortestPathResponse shortestPathResponse);

    /**
     * @param handshakeConfirmationResponse
     * @return
     */
    byte[] print(HandshakeConfirmationResponse handshakeConfirmationResponse);
}

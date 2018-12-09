package org.collibra.challenge.protocol;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.collibra.challenge.protocol.commands.CloserThanResponse;
import org.collibra.challenge.protocol.commands.CommandNotRecognizedResponse;
import org.collibra.challenge.protocol.commands.EdgeOperationResponse;
import org.collibra.challenge.protocol.commands.HandshakeConfirmationResponse;
import org.collibra.challenge.protocol.commands.HandshakeStartResponse;
import org.collibra.challenge.protocol.commands.NodeOperationErrorResponse;
import org.collibra.challenge.protocol.commands.NodeOperationErrorResponse.ErrorType;
import org.collibra.challenge.protocol.commands.NodeOperationResponse;
import org.collibra.challenge.protocol.commands.SessionClosedResponse;
import org.collibra.challenge.protocol.commands.ShortestPathResponse;

class DefaultProtocolPrinter implements ProtocolPrinter {

    private static final Logger LOG = LogManager.getLogger();

    private static final String HI_COMMAND_TEMPLATE = "HI, I'M %s";

    private static final String HI_CONFIRMATION_COMMAND_TEMPLATE = "HI %s";

    private final static String SESSION_CLOSED_COMMAND_PATTERN = "BYE %s, WE SPOKE FOR %d MS";

    @Override
    public byte[] print(CloserThanResponse closerThanResponse)
    {
        List<String> paths = closerThanResponse.getPaths();
        return toBytes( paths.stream().collect( Collectors.joining( "," ) ) );

    }

    @Override
    public byte[] print(CommandNotRecognizedResponse commandNotRecognizedResponse)
    {
        return toBytes( "SORRY, I DIDN'T UNDERSTAND THAT" );
    }

    @Override
    public byte[] print(EdgeOperationResponse edgeOperationResponse)
    {
        String responseString;
        if ( edgeOperationResponse.isRemoved() ) {
            responseString = "EDGE REMOVED";
        }
        else {
            responseString = "EDGE ADDED";
        }
        return toBytes( responseString );
    }

    @Override
    public byte[] print(HandshakeStartResponse handshakeStartResponse)
    {
        return toBytes( String.format( HI_COMMAND_TEMPLATE, handshakeStartResponse.getSessionId().toString() ) );
    }

    @Override
    public byte[] print(NodeOperationErrorResponse nodeOperationErrorResponse)
    {
        ErrorType errorType = nodeOperationErrorResponse.getErrorType();
        String reponseString;
        switch (errorType) {
        case NotFound:
            reponseString = "ERROR: NODE NOT FOUND";
            break;
        case AlreadyExists:
            reponseString = "ERROR: NODE ALREADY EXISTS";
            break;
        default:
            throw new IllegalStateException( "Unknown error type: " + errorType );
        }

        return toBytes( reponseString );

    }

    @Override
    public byte[] print(NodeOperationResponse nodeOperationResponse)
    {
        String response;
        if ( nodeOperationResponse.isRemoved() ) {
            response = "NODE REMOVED";
        }
        else {
            response = "NODE ADDED";
        }
        return toBytes( response );
    }

    @Override
    public byte[] print(SessionClosedResponse sessionClosedResponse)
    {
        String name = sessionClosedResponse.getName();
        Duration sessionDuration = sessionClosedResponse.getSessionDuration();
        String response = String.format( SESSION_CLOSED_COMMAND_PATTERN, name == null ? "" : name, sessionDuration.toMillis() );

        return toBytes( response );
    }

    @Override
    public byte[] print(ShortestPathResponse shortestPathResponse)
    {
        return toBytes( String.valueOf( shortestPathResponse.getShortesPathWeightsSum() ) );
    }

    @Override
    public byte[] print(HandshakeConfirmationResponse handshakeConfirmationResponse)
    {
        return toBytes( String.format( HI_CONFIRMATION_COMMAND_TEMPLATE, handshakeConfirmationResponse.getName() ) );
    }

    private byte[] toBytes(String command)
    {
        return ( command + "\n" ).getBytes( StandardCharsets.US_ASCII );
    }
}

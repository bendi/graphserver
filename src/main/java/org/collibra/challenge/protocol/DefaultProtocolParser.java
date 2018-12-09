package org.collibra.challenge.protocol;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.collibra.challenge.protocol.commands.AddEdgeRequest;
import org.collibra.challenge.protocol.commands.AddNodeRequest;
import org.collibra.challenge.protocol.commands.CloserThanRequest;
import org.collibra.challenge.protocol.commands.HandshakeStartRequest;
import org.collibra.challenge.protocol.commands.RemoveEdgeRequest;
import org.collibra.challenge.protocol.commands.RemoveNodeRequest;
import org.collibra.challenge.protocol.commands.Request;
import org.collibra.challenge.protocol.commands.SessionClosedRequest;
import org.collibra.challenge.protocol.commands.ShortestPathRequest;
import org.collibra.challenge.protocol.exception.UnsupportedCommandException;

public class DefaultProtocolParser implements ProtocolParser {

    private static final Logger LOG = LogManager.getLogger();

    private static final String HI_I_M = "HI, I'M ";

    private static final String BYE_MATE = "BYE MATE";

    private static final String ADD_NODE = "ADD NODE ";

    private static final String ADD_EDGE = "ADD EDGE ";

    private static final String REMOVE_NODE = "REMOVE NODE ";

    private static final String REMOVE_EDGE = "REMOVE EDGE ";

    private static final String SHORTEST_PATH = "SHORTEST PATH ";

    private static final String CLOSER_THAN = "CLOSER THAN ";

    @Override
    public Request parse(byte[] commandBytes) throws UnsupportedCommandException
    {
        String command = new String( commandBytes, StandardCharsets.US_ASCII );

        LOG.info( "Received command: {}", command );

        if ( command.startsWith( HI_I_M ) ) {
            String name = command.substring( HI_I_M.length() );
            return new HandshakeStartRequest( name );
        }
        else if ( command.startsWith( BYE_MATE ) ) {
            return new SessionClosedRequest();
        }
        else if ( command.startsWith( ADD_NODE ) ) {
            String name = command.substring( ( ADD_NODE ).length() );
            return new AddNodeRequest( name );
        }
        else if ( command.startsWith( ADD_EDGE ) ) {
            String[] edgeItems = command.substring( ADD_EDGE.length() ).split( "\\s+" );
            if ( edgeItems.length < 3 ) {
                throw new UnsupportedCommandException( command );
            }
            return new AddEdgeRequest( edgeItems[ 0 ], edgeItems[ 1 ], Integer.parseInt( edgeItems[ 2 ] ) );
        }
        else if ( command.startsWith( REMOVE_NODE ) ) {
            String name = command.substring( REMOVE_NODE.length() );
            return new RemoveNodeRequest( name );
        }
        else if ( command.startsWith( REMOVE_EDGE ) ) {
            String[] edgeItems = command.substring( REMOVE_EDGE.length() ).split( "\\s+" );
            if ( edgeItems.length < 2 ) {
                throw new UnsupportedCommandException( command );
            }
            return new RemoveEdgeRequest( edgeItems[ 0 ], edgeItems[ 1 ] );
        }
        else if ( command.startsWith( SHORTEST_PATH ) ) {
            String[] pathItems = command.substring( SHORTEST_PATH.length() ).split( "\\s+" );
            if ( pathItems.length < 2 ) {
                throw new UnsupportedCommandException( command );
            }
            return new ShortestPathRequest( pathItems[ 0 ], pathItems[ 1 ] );
        }
        else if ( command.startsWith( CLOSER_THAN ) ) {
            String[] weightedNode = command.substring( CLOSER_THAN.length() ).split( "\\s+" );
            if ( weightedNode.length < 2 ) {
                throw new UnsupportedCommandException( command );
            }
            return new CloserThanRequest( weightedNode[ 1 ], Integer.valueOf( weightedNode[ 0 ] ) );
        }
        else {
            throw new UnsupportedCommandException( "Unknown command: " + command );
        }
    }
}


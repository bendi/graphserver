package org.collibra.challenge.protocol.handlers;

import static org.collibra.challenge.protocol.commands.Request.ADD_EDGE;
import static org.collibra.challenge.protocol.commands.Request.ADD_NODE;
import static org.collibra.challenge.protocol.commands.Request.BYE_MATE;
import static org.collibra.challenge.protocol.commands.Request.CLOSER_THAN;
import static org.collibra.challenge.protocol.commands.Request.HI_I_M;
import static org.collibra.challenge.protocol.commands.Request.REMOVE_EDGE;
import static org.collibra.challenge.protocol.commands.Request.REMOVE_NODE;
import static org.collibra.challenge.protocol.commands.Request.SHORTEST_PATH;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import org.collibra.challenge.protocol.commands.AddEdgeRequest;
import org.collibra.challenge.protocol.commands.AddNodeRequest;
import org.collibra.challenge.protocol.commands.CloserThanRequest;
import org.collibra.challenge.protocol.commands.CommandNotRecognizedResponse;
import org.collibra.challenge.protocol.commands.HandshakeStartRequest;
import org.collibra.challenge.protocol.commands.RemoveEdgeRequest;
import org.collibra.challenge.protocol.commands.RemoveNodeRequest;
import org.collibra.challenge.protocol.commands.Request;
import org.collibra.challenge.protocol.commands.SessionClosedRequest;
import org.collibra.challenge.protocol.commands.ShortestPathRequest;
import org.collibra.challenge.protocol.exception.UnsupportedCommandException;

public class RequestDecoder extends ByteToMessageDecoder {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception
    {
        byteBuf.markReaderIndex();
        try {
            byte[] commandBytes = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ( byteBuf.isReadable() ) {
                byte b = byteBuf.readByte();
                if ( b == 10 ) {
                    commandBytes = baos.toByteArray();
                    break;
                }

                baos.write( b );
            }

            if ( commandBytes == null ) {
                byteBuf.resetReaderIndex();
            }
            else {
                Request request = decodeRequest( commandBytes );
                list.add( request );
            }
        }
        catch ( UnsupportedCommandException e ) {
            LOG.warn( "Unsupported command received", e.getMessage() );
            LOG.debug( e );
            channelHandlerContext.writeAndFlush( new CommandNotRecognizedResponse() );
        }
        catch ( Exception e ) {
            LOG.error( "Unknown error happened", e );
            byteBuf.resetReaderIndex();
            channelHandlerContext.writeAndFlush( new CommandNotRecognizedResponse() );
        }
    }

    Request decodeRequest(byte[] commandBytes) throws UnsupportedCommandException
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

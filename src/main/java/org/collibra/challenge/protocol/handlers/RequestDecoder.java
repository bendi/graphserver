package org.collibra.challenge.protocol.handlers;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import org.collibra.challenge.protocol.ProtocolParser;
import org.collibra.challenge.protocol.commands.CommandNotRecognizedResponse;
import org.collibra.challenge.protocol.commands.Request;
import org.collibra.challenge.protocol.exception.UnsupportedCommandException;

public class RequestDecoder extends ByteToMessageDecoder {

    private static final Logger LOG = LogManager.getLogger();

    private static final int ASCII_LINE_FEED = 10;

    private final ProtocolParser protocolParser;

    /**
     * @param protocolParser
     */
    public RequestDecoder(ProtocolParser protocolParser)
    {
        this.protocolParser = protocolParser;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception
    {
        byteBuf.markReaderIndex();
        try {
            byte[] commandBytes = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ( byteBuf.isReadable() ) {
                byte b = byteBuf.readByte();
                if ( b == ASCII_LINE_FEED ) {
                    commandBytes = baos.toByteArray();
                    break;
                }

                baos.write( b );
            }

            if ( commandBytes == null ) {
                byteBuf.resetReaderIndex();
            }
            else {
                Request request = protocolParser.parse( commandBytes );
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

}

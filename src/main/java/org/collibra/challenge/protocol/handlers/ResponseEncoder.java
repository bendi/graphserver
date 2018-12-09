package org.collibra.challenge.protocol.handlers;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import org.collibra.challenge.protocol.ProtocolPrinter;
import org.collibra.challenge.protocol.commands.Response;

public class ResponseEncoder extends ChannelOutboundHandlerAdapter {

    private static final Logger LOG = LogManager.getLogger();

    private final ProtocolPrinter protocolPrinter;

    /**
     *
     * @param protocolPrinter
     */
    public ResponseEncoder(ProtocolPrinter protocolPrinter)
    {
        this.protocolPrinter = protocolPrinter;
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object message, ChannelPromise promise) throws Exception
    {
        if ( message instanceof Response ) {
            sendResponse( channelHandlerContext, (Response) message, promise );
        }
    }

    private void sendResponse(ChannelHandlerContext channelHandlerContext, Response response, ChannelPromise promise)
    {
        byte[] responseBytes = response.print( protocolPrinter );

        ByteBuf byteBuf = channelHandlerContext.alloc().buffer( responseBytes.length );

        byteBuf.writeBytes( responseBytes );

        channelHandlerContext.writeAndFlush( byteBuf, promise );
    }
}

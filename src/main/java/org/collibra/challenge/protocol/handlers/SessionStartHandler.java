package org.collibra.challenge.protocol.handlers;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.collibra.challenge.protocol.commands.HandshakeConfirmationResponse;
import org.collibra.challenge.protocol.commands.HandshakeStartRequest;
import org.collibra.challenge.protocol.commands.HandshakeStartResponse;
import org.collibra.challenge.protocol.event.ConnectionStartedEvent;

public class SessionStartHandler extends MessageToMessageDecoder<HandshakeStartRequest> {

    private static final Logger LOG = LogManager.getLogger();

    private final UUID sessionId;

    /**
     * @param sessionId
     */
    public SessionStartHandler(UUID sessionId)
    {
        this.sessionId = sessionId;
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception
    {
        super.channelActive( channelHandlerContext );

        channelHandlerContext.writeAndFlush( new HandshakeStartResponse( sessionId ) );
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, HandshakeStartRequest handshakeStartRequest, List<Object> list) throws Exception
    {
        try {
            String connectionName = handshakeStartRequest.getName();
            channelHandlerContext.fireUserEventTriggered( new ConnectionStartedEvent( sessionId, connectionName ) );
            channelHandlerContext.writeAndFlush( new HandshakeConfirmationResponse( ( connectionName ) ) ).addListener( (future) -> {
                LOG.info( "Write finished: {}", future.isSuccess() );
            } );
        }
        finally {
            channelHandlerContext.pipeline().remove( this );
        }
    }
}

package org.collibra.challenge.protocol.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.collibra.challenge.protocol.commands.SessionClosedRequest;
import org.collibra.challenge.protocol.commands.SessionClosedResponse;
import org.collibra.challenge.protocol.event.ConnectionStartedEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SessionClosedHandler extends MessageToMessageDecoder<SessionClosedRequest> {

    private static final Logger LOG = LogManager.getLogger();

    private final UUID sessionId;

    private LocalDateTime sessionStartTime;

    private String connectionName;

    /**
     * @param sessionId
     */
    public SessionClosedHandler(UUID sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object userEvent) throws Exception {

        if (userEvent instanceof ConnectionStartedEvent) {
            connectionName = ((ConnectionStartedEvent) userEvent).getConnectionName();
            LOG.info("Connection started with: {},", connectionName);
        } else {
            super.userEventTriggered(channelHandlerContext, userEvent);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);

        sessionStartTime = LocalDateTime.now();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        if (cause instanceof ReadTimeoutException) {
            closeSession(channelHandlerContext);
        }
        super.exceptionCaught(channelHandlerContext, cause);
    }

    private void closeSession(ChannelHandlerContext channelHandlerContext) {
        Duration sessionDuration = Duration.between(sessionStartTime, LocalDateTime.now());

        LOG.info("Closing session: {}", sessionId);
        channelHandlerContext.writeAndFlush(new SessionClosedResponse(connectionName, sessionDuration));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, SessionClosedRequest sessionClosedRequest, List<Object> list) throws Exception {
        closeSession(channelHandlerContext);
    }
}

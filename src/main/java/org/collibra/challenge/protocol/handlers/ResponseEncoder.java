package org.collibra.challenge.protocol.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.collibra.challenge.protocol.commands.Response;

import java.nio.charset.StandardCharsets;

public class ResponseEncoder extends ChannelOutboundHandlerAdapter {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object message, ChannelPromise promise) throws Exception {
        if (message instanceof Response) {
            sendResponse(channelHandlerContext, (Response) message, promise);
        }
    }

    private void sendResponse(ChannelHandlerContext channelHandlerContext, Response response, ChannelPromise promise) {
        String responseString = response.toResponseString();

        LOG.info("Responding with: {}", responseString);

        String responseWithTerminator = responseString + "\n";

        byte[] responseBytes = responseWithTerminator.getBytes(StandardCharsets.US_ASCII);

        ByteBuf byteBuf = channelHandlerContext.alloc().buffer(responseBytes.length);

        byteBuf.writeBytes(responseBytes);

        channelHandlerContext.writeAndFlush(byteBuf, promise).addListener((future) -> {
            LOG.info("Write finished, {}", responseString);
        });
    }
}

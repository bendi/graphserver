package org.collibra.challenge.protocol.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.collibra.challenge.protocol.commands.CommandNotRecognized;
import org.collibra.challenge.protocol.commands.HandshakeStartRequest;
import org.collibra.challenge.protocol.commands.Request;
import org.collibra.challenge.protocol.commands.SessionClosedRequest;
import org.collibra.challenge.protocol.exception.UnsupportedCommandException;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RequestDecoder extends ByteToMessageDecoder {

    private static final Logger LOG = LogManager.getLogger();

    private static final int COMMAND_MAX_LENGTH = 200;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        try {
            byte[] commandBytes = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int commandLength = 0;
            while (byteBuf.isReadable()) {
                byte b = byteBuf.readByte();
                if (b == 10) {
                    commandBytes = baos.toByteArray();
                    break;
                }

                baos.write(b);

                if (commandLength++ > COMMAND_MAX_LENGTH) {
                    throw new UnsupportedCommandException("Command too long");
                }
            }

            if (commandBytes == null) {
                byteBuf.resetReaderIndex();
            } else {
                byteBuf.discardReadBytes();
                Request request = decodeRequest(commandBytes);
                list.add(request);
            }
        } catch (UnsupportedCommandException e) {
            LOG.warn("Unsupported command received", e.getMessage());
            LOG.debug(e);
            channelHandlerContext.writeAndFlush(new CommandNotRecognized());
        } catch (Exception e) {
            LOG.error("Unknown error happened", e);
            byteBuf.resetReaderIndex();
        }
    }


    Request decodeRequest(byte[] commandBytes) throws UnsupportedCommandException {
        String command = new String(commandBytes, StandardCharsets.US_ASCII);

        LOG.info("Received command: {}", command);

        if (command.startsWith("HI, I'M ")) {
            // handle HI
            String name = command.substring("HI, I'M ".length());
            return new HandshakeStartRequest(name);
        } else if (command.startsWith("BYE MATE")) {
            return new SessionClosedRequest();
        } else if (command.startsWith("ADD NODE")) {

        } else if (command.startsWith("ADD EDGE")) {

        } else if (command.startsWith("REMOVE NODE")) {

        } else if (command.startsWith("REMOVE EDGE")) {

        } else {
            throw new UnsupportedCommandException("Unknown command: " + command);
        }

        // FIXME [mb] - after adding support for all commands this should go away
        return null;
    }
}

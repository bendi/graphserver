package org.collibra.challenge.protocol.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.collibra.challenge.protocol.commands.*;
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
            channelHandlerContext.writeAndFlush(new CommandNotRecognizedResponse());
        } catch (Exception e) {
            LOG.error("Unknown error happened", e);
            byteBuf.resetReaderIndex();
            channelHandlerContext.writeAndFlush(new CommandNotRecognizedResponse());
        }
    }


    Request decodeRequest(byte[] commandBytes) throws UnsupportedCommandException {
        String command = new String(commandBytes, StandardCharsets.US_ASCII);

        LOG.info("Received command: {}", command);

        if (command.startsWith("HI, I'M ")) {
            String name = command.substring("HI, I'M ".length());
            return new HandshakeStartRequest(name);
        } else if (command.startsWith("BYE MATE")) {
            return new SessionClosedRequest();
        } else if (command.startsWith("ADD NODE ")) {
            String name = command.substring(("ADD NODE ").length());
            return new AddNodeRequest(name);
        } else if (command.startsWith("ADD EDGE ")) {
            String[] edgeItems = command.substring("ADD EDGE ".length()).split(" ");
            if (edgeItems.length < 3) {
                throw new UnsupportedCommandException(command);
            }
            return new AddEdgeRequest(edgeItems[0], edgeItems[1], Integer.parseInt(edgeItems[2]));
        } else if (command.startsWith("REMOVE NODE ")) {
            String name = command.substring("REMOVE NODE ".length());
            return new RemoveNodeRequest(name);
        } else if (command.startsWith("REMOVE EDGE ")) {
            String[] edgeItems = command.substring("REMOVE EDGE ".length()).split(" ");
            if (edgeItems.length < 2) {
                throw new UnsupportedCommandException(command);
            }
            return new RemoveEdgeRequest(edgeItems[0], edgeItems[1]);
        } else {
            throw new UnsupportedCommandException("Unknown command: " + command);
        }
    }
}

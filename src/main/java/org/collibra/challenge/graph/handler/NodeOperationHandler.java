package org.collibra.challenge.graph.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.collibra.challenge.graph.error.GraphOperationException;
import org.collibra.challenge.graph.error.GraphOperationException.*;
import org.collibra.challenge.graph.manager.VerticesManager;
import org.collibra.challenge.protocol.commands.*;
import org.collibra.challenge.protocol.commands.NodeOperationErrorResponse.*;

import java.util.List;

public class NodeOperationHandler extends MessageToMessageDecoder<Request> {

    private static final Logger LOG = LogManager.getLogger();

    private final VerticesManager verticesManager;

    /**
     * @param verticesManager
     */
    public NodeOperationHandler(VerticesManager verticesManager) {
        this.verticesManager = verticesManager;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Request request, List<Object> list) throws Exception {
        try {
            Response response = handleGraphOperationRequest(request);
            channelHandlerContext.writeAndFlush(response);
        } catch (NodeMissingException e) {
            channelHandlerContext.writeAndFlush(new NodeOperationErrorResponse(ErrorType.NotFound));
        } catch (NodeAlreadyExistsException e) {
            channelHandlerContext.writeAndFlush(new NodeOperationErrorResponse(ErrorType.AlreadyExists));
        } catch (Exception e) {
            LOG.error("Unknown error happened.", e);
            channelHandlerContext.writeAndFlush(new CommandNotRecognizedResponse());
        }
    }

    private Response handleGraphOperationRequest(Request request) throws GraphOperationException {
        if (request instanceof AddEdgeRequest) {
            verticesManager.addEdge((AddEdgeRequest) request);
            return new EdgeOperationResponse();
        } else if (request instanceof AddNodeRequest) {
            verticesManager.addNode((AddNodeRequest) request);
            return new NodeOperationResponse();
        } else if (request instanceof RemoveEdgeRequest) {
            verticesManager.removeEdge((RemoveEdgeRequest) request);
            return new EdgeOperationResponse(true);
        } else if (request instanceof RemoveNodeRequest) {
            verticesManager.removeNode((RemoveNodeRequest) request);
            return new NodeOperationResponse(true);
        } else {
            throw new IllegalArgumentException("Unknonw request: " + request.getClass().getCanonicalName());
        }
    }

}

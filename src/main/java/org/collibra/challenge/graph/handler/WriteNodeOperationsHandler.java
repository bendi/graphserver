package org.collibra.challenge.graph.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.collibra.challenge.graph.error.NodeOperationException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeAlreadyExistsException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeMissingException;
import org.collibra.challenge.graph.manager.NodeOperationManager;
import org.collibra.challenge.protocol.commands.AddEdgeRequest;
import org.collibra.challenge.protocol.commands.AddNodeRequest;
import org.collibra.challenge.protocol.commands.CommandNotRecognizedResponse;
import org.collibra.challenge.protocol.commands.EdgeOperationResponse;
import org.collibra.challenge.protocol.commands.NodeOperationErrorResponse;
import org.collibra.challenge.protocol.commands.NodeOperationErrorResponse.ErrorType;
import org.collibra.challenge.protocol.commands.NodeOperationResponse;
import org.collibra.challenge.protocol.commands.RemoveEdgeRequest;
import org.collibra.challenge.protocol.commands.RemoveNodeRequest;
import org.collibra.challenge.protocol.commands.Request;
import org.collibra.challenge.protocol.commands.Response;
import org.collibra.challenge.protocol.commands.WriteRequest;

public class WriteNodeOperationsHandler extends MessageToMessageDecoder<WriteRequest> {

    private static final Logger LOG = LogManager.getLogger();

    private final NodeOperationManager nodeOperationManager;

    /**
     * @param nodeOperationManager
     */
    public WriteNodeOperationsHandler(NodeOperationManager nodeOperationManager)
    {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WriteRequest request, List<Object> list) throws Exception
    {
        try {
            Response response = handleGraphOperationRequest( request );
            channelHandlerContext.writeAndFlush( response );
        }
        catch ( NodeMissingException e ) {
            channelHandlerContext.writeAndFlush( new NodeOperationErrorResponse( ErrorType.NotFound ) );
        }
        catch ( NodeAlreadyExistsException e ) {
            channelHandlerContext.writeAndFlush( new NodeOperationErrorResponse( ErrorType.AlreadyExists ) );
        }
        catch ( Exception e ) {
            LOG.error( "Unknown error happened.", e );
            channelHandlerContext.writeAndFlush( new CommandNotRecognizedResponse() );
        }
    }

    private Response handleGraphOperationRequest(Request request) throws NodeOperationException
    {
        if ( request instanceof AddEdgeRequest ) {
            handleGraphOperationRequest( (AddEdgeRequest) request );
            return new EdgeOperationResponse();
        }
        else if ( request instanceof AddNodeRequest ) {
            handleGraphOperationRequest( (AddNodeRequest) request );
            return new NodeOperationResponse();
        }
        else if ( request instanceof RemoveEdgeRequest ) {
            handleGraphOperationRequest( (RemoveEdgeRequest) request );
            return new EdgeOperationResponse( true );
        }
        else if ( request instanceof RemoveNodeRequest ) {
            handleGraphOperationRequest( (RemoveNodeRequest) request );
            return new NodeOperationResponse( true );
        }
        else {
            throw new IllegalArgumentException( "Unknonw request: " + request.getClass().getCanonicalName() );
        }
    }

    private void handleGraphOperationRequest(AddEdgeRequest addEdgeRequest) throws NodeOperationException
    {
        nodeOperationManager.addEdge( addEdgeRequest.getStartNode(), addEdgeRequest.getEndNode(), addEdgeRequest.getWeight() );
    }

    private void handleGraphOperationRequest(AddNodeRequest addNodeRequest) throws NodeOperationException
    {
        nodeOperationManager.addNode( addNodeRequest.getNodeName() );
    }

    private void handleGraphOperationRequest(RemoveEdgeRequest removeEdgeRequest) throws NodeOperationException
    {
        nodeOperationManager.removeEdge( removeEdgeRequest.getStartNode(), removeEdgeRequest.getEndNode() );
    }

    private void handleGraphOperationRequest(RemoveNodeRequest removeNodeRequest) throws NodeOperationException
    {
        nodeOperationManager.removeNode( removeNodeRequest.getNodeName() );
    }

}

package org.collibra.challenge.graph.handler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.collibra.challenge.graph.manager.NodeOperationManager;
import org.collibra.challenge.protocol.commands.ReadRequest;
import org.collibra.challenge.protocol.commands.ShortestPathRequest;
import org.collibra.challenge.protocol.commands.ShortestPathResponse;

public class ReadOperationsHandler extends MessageToMessageDecoder<ReadRequest> {

    private final NodeOperationManager nodeOperationManager;

    /**
     * @param nodeOperationManager
     */
    public ReadOperationsHandler(NodeOperationManager nodeOperationManager)
    {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ReadRequest readRequest, List<Object> list) throws Exception
    {
        if ( readRequest instanceof ShortestPathRequest ) {
            Integer value = handleReadRequest( (ShortestPathRequest) readRequest );
            channelHandlerContext.writeAndFlush( new ShortestPathResponse( value ) );
        }
    }

    private Integer handleReadRequest(ShortestPathRequest readRequest)
    {
        return nodeOperationManager.findShortestPath( readRequest.getFromNode(), readRequest.getToNode() );
    }
}

package org.collibra.challenge.graph.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.collibra.challenge.graph.manager.NodeOperationManager;
import org.collibra.challenge.protocol.commands.ReadRequest;
import org.collibra.challenge.protocol.commands.ShortestPathRequest;
import org.collibra.challenge.protocol.commands.ShortestPathResponse;

public class ReadOperationsHandler extends MessageToMessageDecoder<ReadRequest> {

    private static final Logger LOG = LogManager.getLogger();

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
        try {
            if ( readRequest instanceof ShortestPathRequest ) {
                Integer value = handleReadRequest( (ShortestPathRequest) readRequest );
                channelHandlerContext.writeAndFlush( new ShortestPathResponse( value ) );
            }
        } catch ( Exception e ) {
            LOG.error( "Exception computing shortest path", e );
        }
    }

    private Integer handleReadRequest(ShortestPathRequest readRequest)
    {
        return nodeOperationManager.findShortestPath( readRequest.getFromNode(), readRequest.getToNode() );
    }
}

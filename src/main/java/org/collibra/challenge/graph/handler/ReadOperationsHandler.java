package org.collibra.challenge.graph.handler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import org.collibra.challenge.graph.manager.NodeOperationManager;

public class ReadOperationsHandler extends MessageToMessageDecoder<ReadOperationsHandler> {

    private final NodeOperationManager nodeOperationManager;

    /**
     *
     * @param nodeOperationManager
     */
    public ReadOperationsHandler(NodeOperationManager nodeOperationManager)
    {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ReadOperationsHandler readOperationsHandler, List<Object> list) throws Exception
    {

    }
}

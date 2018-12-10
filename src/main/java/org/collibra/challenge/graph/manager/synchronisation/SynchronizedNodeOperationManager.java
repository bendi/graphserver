package org.collibra.challenge.graph.manager.synchronisation;

import java.util.List;

import org.collibra.challenge.graph.exception.NodeOperationException;
import org.collibra.challenge.graph.manager.NodeOperationManager;

/**
 * The simplies synchronisation proxy - using <em>synchronised</em> keyword
 */
public class SynchronizedNodeOperationManager implements NodeOperationManager {

    private final NodeOperationManager nodeOperationManager;

    /**
     * @param nodeOperationManager
     */
    public SynchronizedNodeOperationManager(NodeOperationManager nodeOperationManager)
    {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    public synchronized void addNode(String nodeName) throws NodeOperationException
    {
        nodeOperationManager.addNode( nodeName );
    }

    @Override
    public synchronized void addEdge(String startNode, String endNode, int weight) throws NodeOperationException
    {
        nodeOperationManager.addEdge( startNode, endNode, weight );
    }

    @Override
    public synchronized void removeNode(String nodeName) throws NodeOperationException
    {
        nodeOperationManager.removeNode( nodeName );
    }

    @Override
    public synchronized void removeEdge(String startNode, String endNode) throws NodeOperationException
    {
        nodeOperationManager.removeEdge( startNode, endNode );
    }

    @Override
    public synchronized Integer findShortestPath(String fromNode, String toNode)
    {
        return nodeOperationManager.findShortestPath( fromNode, toNode );
    }

    @Override
    public synchronized List<String> findCloserThan(String nodeName, Integer weight)
    {
        return nodeOperationManager.findCloserThan( nodeName, weight );
    }
}

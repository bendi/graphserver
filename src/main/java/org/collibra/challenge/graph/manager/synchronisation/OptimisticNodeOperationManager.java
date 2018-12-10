package org.collibra.challenge.graph.manager.synchronisation;

import java.util.List;
import java.util.concurrent.locks.StampedLock;

import org.collibra.challenge.graph.exception.NodeOperationException;
import org.collibra.challenge.graph.manager.NodeOperationManager;

/**
 * Optimisitic locking proxy around NodeOperationManager
 */
public class OptimisticNodeOperationManager implements NodeOperationManager {

    private final NodeOperationManager nodeOperationManager;

    private final StampedLock stampedLock = new StampedLock();

    /**
     * @param nodeOperationManager
     */
    public OptimisticNodeOperationManager(NodeOperationManager nodeOperationManager)
    {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    public void addNode(String nodeName) throws NodeOperationException
    {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.addNode( nodeName );
        }
        finally {
            stampedLock.unlockWrite( lock );
        }
    }

    @Override
    public void addEdge(String startNode, String endNode, int weight) throws NodeOperationException
    {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.addEdge( startNode, endNode, weight );
        }
        finally {
            stampedLock.unlockWrite( lock );
        }
    }

    @Override
    public void removeNode(String nodeName) throws NodeOperationException
    {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.removeNode( nodeName );
        }
        finally {
            stampedLock.unlockWrite( lock );
        }
    }

    @Override
    public void removeEdge(String startNode, String endNode) throws NodeOperationException
    {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.removeEdge( startNode, endNode );
        }
        finally {
            stampedLock.unlockWrite( lock );
        }
    }

    @Override
    public Integer findShortestPath(String fromNode, String toNode)
    {
        long stamp = stampedLock.tryOptimisticRead();
        Integer value = nodeOperationManager.findShortestPath( fromNode, toNode );
        if ( !stampedLock.validate( stamp ) ) {
            stamp = stampedLock.readLock();
            try {
                value = nodeOperationManager.findShortestPath( fromNode, toNode );
            }
            finally {
                stampedLock.unlockRead( stamp );
            }
        }
        return value;
    }

    @Override
    public List<String> findCloserThan(String nodeName, Integer weight)
    {
        long stamp = stampedLock.tryOptimisticRead();
        List<String> nodes = nodeOperationManager.findCloserThan( nodeName, weight );
        if ( !stampedLock.validate( stamp ) ) {
            stamp = stampedLock.readLock();
            try {
                nodes = nodeOperationManager.findCloserThan( nodeName, weight );
            }
            finally {
                stampedLock.unlockRead( stamp );
            }
        }
        return nodes;
    }
}

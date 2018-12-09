package org.collibra.challenge.graph.manager;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.collibra.challenge.graph.exception.NodeOperationException;

/**
 * ReadWriteLock proxy around NodeOperationManager
 */
public class ReentrantReadWriteLockNodeOperationManager implements NodeOperationManager {

    private final ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private final NodeOperationManager nodeOperationManager;

    /**
     * @param nodeOperationManager
     */
    public ReentrantReadWriteLockNodeOperationManager(NodeOperationManager nodeOperationManager)
    {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    public void addNode(String nodeName) throws NodeOperationException
    {
        reentrantReadWriteLock.writeLock().lock();
        try {
            nodeOperationManager.addNode( nodeName );
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void addEdge(String startNode, String endNode, int weight) throws NodeOperationException
    {
        reentrantReadWriteLock.writeLock().lock();
        try {
            nodeOperationManager.addEdge( startNode, endNode, weight );
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void removeNode(String nodeName) throws NodeOperationException
    {
        reentrantReadWriteLock.writeLock().lock();
        try {
            nodeOperationManager.removeNode( nodeName );
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void removeEdge(String startNode, String endNode) throws NodeOperationException
    {
        reentrantReadWriteLock.writeLock().lock();
        try {
            nodeOperationManager.removeEdge( startNode, endNode );
        }
        finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    @Override
    public Integer findShortestPath(String fromNode, String toNode)
    {
        reentrantReadWriteLock.readLock().lock();
        try {
            return nodeOperationManager.findShortestPath( fromNode, toNode );
        }
        finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    @Override
    public List<String> findCloserThan(String nodeName, Integer weight)
    {
        reentrantReadWriteLock.readLock().lock();
        try {
            return nodeOperationManager.findCloserThan( nodeName, weight );
        }
        finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
}

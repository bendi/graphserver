package org.collibra.challenge.graph.manager;

import org.collibra.challenge.graph.error.NodeOperationException;

import java.util.concurrent.locks.StampedLock;

public class OptimisticNodeOperationManager implements NodeOperationManager {

    private final NodeOperationManager nodeOperationManager;

    private final StampedLock stampedLock = new StampedLock();

    /**
     * @param nodeOperationManager
     */
    public OptimisticNodeOperationManager(NodeOperationManager nodeOperationManager) {
        this.nodeOperationManager = nodeOperationManager;
    }

    @Override
    public void addNode(String nodeName) throws NodeOperationException {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.addNode(nodeName);
        } finally {
            stampedLock.unlockWrite(lock);
        }
    }

    @Override
    public void addEdge(String startNode, String endNode, int weight) throws NodeOperationException {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.addEdge(startNode, endNode, weight);
        } finally {
            stampedLock.unlockWrite(lock);
        }
    }

    @Override
    public void removeNode(String nodeName) throws NodeOperationException {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.removeNode(nodeName);
        } finally {
            stampedLock.unlockWrite(lock);
        }
    }

    @Override
    public void removeEdge(String startNode, String endNode) throws NodeOperationException {
        long lock = stampedLock.writeLock();
        try {
            nodeOperationManager.removeEdge(startNode, endNode);
        } finally {
            stampedLock.unlockWrite(lock);
        }
    }
}

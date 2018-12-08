package org.collibra.challenge.graph.manager;

import org.collibra.challenge.graph.error.NodeOperationException;

public interface NodeOperationManager {

    /**
     * @param nodeName
     * @throws NodeOperationException
     */
    void addNode(String nodeName) throws NodeOperationException;

    /**
     * @param startNode
     * @param endNode
     * @param weight
     * @throws NodeOperationException
     */
    void addEdge(String startNode, String endNode, int weight) throws NodeOperationException;

    /**
     * @param nodeName
     * @throws NodeOperationException
     */
    void removeNode(String nodeName) throws NodeOperationException;

    /**
     * @param startNode
     * @param endNode
     * @throws NodeOperationException
     */
    void removeEdge(String startNode, String endNode) throws NodeOperationException;

}

package org.collibra.challenge.graph.manager;

import java.util.List;

import org.collibra.challenge.graph.exception.NodeOperationException;

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

    /**
     * @param fromNode
     * @param toNode
     * @return
     */
    Integer findShortestPath(String fromNode, String toNode);

    /**
     * @param nodeName
     * @param weight
     * @return
     */
    List<String> findCloserThan(String nodeName, Integer weight);

}

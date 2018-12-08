package org.collibra.challenge.graph.manager;

import org.collibra.challenge.graph.error.GraphOperationException;
import org.collibra.challenge.protocol.commands.AddEdgeRequest;
import org.collibra.challenge.protocol.commands.AddNodeRequest;
import org.collibra.challenge.protocol.commands.RemoveEdgeRequest;
import org.collibra.challenge.protocol.commands.RemoveNodeRequest;

public interface VerticesManager {

    /**
     *
     * @param addNodeRequest
     * @throws GraphOperationException
     */
    void addNode(AddNodeRequest addNodeRequest) throws GraphOperationException;

    /**
     *
     * @param addEdgeRequest
     * @throws GraphOperationException
     */
    void addEdge(AddEdgeRequest addEdgeRequest) throws GraphOperationException;

    /**
     *
     * @param removeNodeRequest
     * @throws GraphOperationException
     */
    void removeNode(RemoveNodeRequest removeNodeRequest) throws GraphOperationException;

    /**
     *
     * @param removeEdgeRequest
     * @throws GraphOperationException
     */
    void removeEdge(RemoveEdgeRequest removeEdgeRequest) throws GraphOperationException;

}

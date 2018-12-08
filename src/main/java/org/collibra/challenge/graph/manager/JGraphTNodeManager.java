package org.collibra.challenge.graph.manager;

import org.collibra.challenge.graph.error.NodeOperationException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeAlreadyExistsException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeMissingException;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;

public class JGraphTNodeManager implements NodeOperationManager {

    private final MySimpleGraph completeGraph;

    public JGraphTNodeManager()
    {
        completeGraph = new MySimpleGraph( new ClassBasedEdgeFactory<>( DefaultWeightedEdge.class ) );
    }

    @Override
    public void addNode(String nodeName) throws NodeOperationException
    {
        if ( !completeGraph.addVertex( nodeName ) ) {
            throw new NodeAlreadyExistsException();
        }
    }

    @Override
    public void addEdge(String startNode, String endNode, int weight) throws NodeOperationException
    {
        if ( !completeGraph.containsVertex( startNode ) || !completeGraph.containsVertex( endNode ) ) {
            throw new NodeMissingException();
        }
        DefaultWeightedEdge edge = completeGraph.addEdge( startNode, endNode );
        if ( edge == null ) {
            throw new NodeAlreadyExistsException();
        }
        completeGraph.setEdgeWeight( edge, weight );
    }

    @Override
    public void removeNode(String nodeName) throws NodeOperationException
    {
        if ( !completeGraph.removeVertex( nodeName ) ) {
            throw new NodeMissingException();
        }
    }

    @Override
    public void removeEdge(String startNode, String endNode) throws NodeOperationException
    {
        if ( completeGraph.removeEdge( startNode, endNode ) == null ) {
            throw new NodeMissingException();
        }
    }

    private static class MySimpleGraph extends AbstractBaseGraph<String, DefaultWeightedEdge>
            implements WeightedGraph<String, DefaultWeightedEdge>, DirectedGraph<String, DefaultWeightedEdge> {

        protected MySimpleGraph(EdgeFactory<String, DefaultWeightedEdge> ef)
        {
            super( ef, true, false );
        }
    }
}

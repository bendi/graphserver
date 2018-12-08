package org.collibra.challenge.graph.manager;

import java.util.ArrayList;
import java.util.List;

import org.collibra.challenge.graph.error.NodeOperationException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeAlreadyExistsException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeMissingException;
import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
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

    @Override
    public Integer findShortestPath(String fromNode, String toNode)
    {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath( completeGraph );
        try {
            GraphPath path = dijkstraShortestPath.getPath( fromNode, toNode );
            if ( path == null ) {
                return Integer.MAX_VALUE;
            }
            return Double.valueOf( path.getWeight() ).intValue();
        }
        catch ( IllegalArgumentException e ) {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public List<String> findCloserThan(String nodeName, Integer weight)
    {
        DijkstraShortestPath dijkstraShortestPath = new DijkstraShortestPath( completeGraph, weight );
        SingleSourcePaths paths = dijkstraShortestPath.getPaths( nodeName );
        if ( paths == null ) {
            return null;
        }
        return new ArrayList<>( paths.getGraph().edgeSet() );
    }

    private static class MySimpleGraph extends AbstractBaseGraph<String, DefaultWeightedEdge>
            implements WeightedGraph<String, DefaultWeightedEdge>, DirectedGraph<String, DefaultWeightedEdge> {

        protected MySimpleGraph(EdgeFactory<String, DefaultWeightedEdge> ef)
        {
            super( ef, true, true );
        }
    }
}

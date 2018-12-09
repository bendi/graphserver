package org.collibra.challenge.graph.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.collibra.challenge.graph.exception.NodeOperationException;
import org.collibra.challenge.graph.exception.NodeOperationException.NodeAlreadyExistsException;
import org.collibra.challenge.graph.exception.NodeOperationException.NodeMissingException;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.traverse.ClosestFirstIterator;

/**
 * Directed graph workhorse - responsible for managing all node/edge operations
 */
public class JGraphTNodeManager implements NodeOperationManager {

    private static final Logger LOG = LogManager.getLogger();

    private final Graph<String, DefaultWeightedEdge> completeGraph;

    public JGraphTNodeManager()
    {
        completeGraph = GraphTypeBuilder.<String, DefaultWeightedEdge>directed().allowingMultipleEdges( true )
                .allowingSelfLoops( true )
                .edgeClass( DefaultWeightedEdge.class )
                .weighted( true )
                .buildGraph();
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
        if ( !completeGraph.containsVertex( startNode ) || !completeGraph.containsVertex( endNode ) ) {
            throw new NodeMissingException();
        }

        completeGraph.removeEdge( startNode, endNode );
    }

    @Override
    public Integer findShortestPath(String fromNode, String toNode)
    {
        ShortestPathAlgorithm<String, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>( completeGraph );
        try {
            GraphPath<String, DefaultWeightedEdge> path = dijkstraShortestPath.getPath( fromNode, toNode );
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
        ClosestFirstIterator<String, DefaultWeightedEdge> cfi = new ClosestFirstIterator<>( completeGraph, nodeName, weight - 0.2 );

        List<String> result = new ArrayList<>();

        cfi.forEachRemaining( result::add );

        result.remove( nodeName );

        Collections.sort( result );

        return result;
    }

}

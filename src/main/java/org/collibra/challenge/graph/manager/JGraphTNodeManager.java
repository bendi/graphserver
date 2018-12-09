package org.collibra.challenge.graph.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.collibra.challenge.graph.error.NodeOperationException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeAlreadyExistsException;
import org.collibra.challenge.graph.error.NodeOperationException.NodeMissingException;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.JohnsonShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

public class JGraphTNodeManager implements NodeOperationManager {

    private static final Logger LOG = LogManager.getLogger();

    private final Graph<String, DefaultWeightedEdge> completeGraph;

    public JGraphTNodeManager()
    {
        completeGraph = GraphTypeBuilder.<String, DefaultWeightedEdge>directed()
                .allowingMultipleEdges( true )
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
        ShortestPathAlgorithm shortestPath = new BellmanFordShortestPath( completeGraph, weight );
        SingleSourcePaths paths = shortestPath.getPaths( nodeName );
        if ( paths == null ) {
            return null;
        }

        Set<String> vertexSet = paths.getGraph().vertexSet();

        List<String> ret = new ArrayList<>( vertexSet );
        ret.remove( nodeName );
        return ret;
    }

}

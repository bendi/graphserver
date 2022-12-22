package codingame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeVisitor2 {

    /**
     * @param graph
     * @param integerDistance
     * @return
     */
    public int computeDistance(Graph graph, IntegerDistance integerDistance)
    {
        Set<Node> visited = new HashSet<>();
        Map<IntegerDistance, Integer> d = new HashMap<>();

        Node startNode = graph.getNode( integerDistance.getStartNode() );
        List<Node> path = new ArrayList<>();

        {
            Collection<Node> edges = startNode.getEdges();
            for (Node edge : edges) {
                computeDistance( d, startNode, edge, 1, false );
            }

            visited.add( startNode );
        }

        for (Node edge : startNode.getEdges()) {
            if ( !visited.contains( edge ) ) {
                computeDistances( visited, d, startNode, edge, path );
            }
        }

        return d.get( integerDistance );
    }

    public Map<IntegerDistance, Integer> computeDistances(Graph graph, IntegerDistance integerDistance)
    {
        Set<Node> visited = new HashSet<>();
        Map<IntegerDistance, Integer> d = new HashMap<>();

        Node startNode = graph.getNode( integerDistance.getStartNode() );
        List<Node> path = new ArrayList<>();

        {
            Collection<Node> edges = startNode.getEdges();
            for (Node edge : edges) {
                computeDistance( d, startNode, edge, 1, false );
            }

            visited.add( startNode );
        }

        for (Node edge : startNode.getEdges()) {
            if ( !visited.contains( edge ) ) {
                computeDistances( visited, d, startNode, edge, path );
            }
        }

        return d;
    }

    private void computeDistances(Set<Node> visited, Map<IntegerDistance, Integer> d, Node startNode, Node nextNode, List<Node> path)
    {
        {
            IntegerDistance id = new IntegerDistance( startNode.getId(), nextNode.getId() );
            if ( !d.containsKey( id ) ) {
                d.put( id, path.size() + 1 );
            }
        }
        Collection<Node> edges = nextNode.getEdges();
        for (Node edge : edges) {
            computeDistance( d, nextNode, edge, 1, false );

            {
                IntegerDistance id = new IntegerDistance( startNode.getId(), edge.getId() );
                if ( !d.containsKey( id ) ) {
                    d.put( id, path.size() + 2 );
                }
            }

            if ( visited.contains( edge ) && !edge.equals( startNode ) ) {
                IntegerDistance id = new IntegerDistance( edge.getId(), startNode.getId() );
                if ( d.containsKey( id ) ) {
                    int distanceToRoot = d.get( id ) + 1;
                    computeDistance( d, startNode, nextNode, distanceToRoot, true );
                    List<Node> p = new ArrayList<>( path );
                    Collections.reverse( p );
                    Iterator<Node> it = p.iterator();
                    int distanceFromCurrent = 1;
                    while ( it.hasNext() ) {
                        Node previous = it.next();
                        computeDistance( d, startNode, previous, distanceToRoot + distanceFromCurrent, true );
                        distanceFromCurrent++;
                    }
                }
            }
        }

        visited.add( startNode );
        visited.add( nextNode );

        for (Node edge : nextNode.getEdges()) {
            if ( !visited.contains( edge ) ) {
                computeDistances( visited, d, startNode, edge, path( path, nextNode ) );
            }
        }
    }

    private void computeDistance(Map<IntegerDistance, Integer> d, Node startNode, Node currentNode, int level, boolean updateOnly)
    {
        IntegerDistance id = new IntegerDistance( startNode.getId(), currentNode.getId() );
        if ( d.containsKey( id ) && d.get( id ) > level ) {
            d.replace( id, level );
        }
        else if ( !updateOnly ) {
            d.put( id, level );
        }
    }

    List<Node> path(List<Node> currentPath, Node nextItem)
    {
        List<Node> newPath = new ArrayList<>( currentPath );
        newPath.add( nextItem );

        return newPath;
    }

}

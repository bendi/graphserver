package codingame;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NodeVisitor {

    public int computeDistance(Graph graph, IntegerDistance integerDistance)
    {
        Map<IntegerDistance, Integer> foundDistances = new HashMap<>();
        Set<Node> visitedNodes = new HashSet<>();

        for (Node node : graph.getNodes()) {
            VisitedNode visitedNode = new VisitedNode( node.getId() );

            if ( visitedNodes.contains( node ) ) {
                continue;
            }

            System.out.println( "Visiting node: " + node );

            updateEdges( visitedNodes, visitedNode, integerDistance, foundDistances, node, 1 );
        }

        return foundDistances.get( integerDistance );
    }

    private void updateEdges(Set<Node> visitedNodes, VisitedNode parent, IntegerDistance integerDistance, Map<IntegerDistance, Integer> foundDistances,
            Node startNode, int level)
    {
        System.out.println( "Updating edges" );
        for (Node edge : startNode.getEdges()) {
            IntegerDistance distanceKey = new IntegerDistance( startNode.getId(), edge.getId() );
            VisitedNode visitedNode = new VisitedNode( edge.getId(), parent );

            System.out.println( "Updating edge: " + edge );

            if ( visitedNodes.contains( edge ) ) {
                System.out.println( "Edge alredy visited: " + edge );
                // check if closer distances can be computed
                verifyDistances( edge, integerDistance, foundDistances, level, false );

                // check if edges can connect to target node
                updateVisitedNodeEdges( new HashSet<>(), edge, edge.getEdges(), integerDistance, foundDistances, 1 );
            }
            else {

                visitedNodes.add( edge );

                foundDistances.put( distanceKey, level );

                updateDistancesToPreviousNodes( parent, foundDistances );

                updateEdges( visitedNodes, visitedNode, integerDistance, foundDistances, edge, level + 1 );
            }
        }
    }

    private void updateDistancesToPreviousNodes(VisitedNode parent, Map<IntegerDistance, Integer> foundDistances)
    {
        Iterator<VisitedNode> it = parent.iterator();

        int distance = 1;
        while ( it.hasNext() ) {
            VisitedNode current = it.next();
            updateDistance( parent.getId(), current.getId(), foundDistances, distance, false );
            distance++;
        }
    }

    private void verifyDistances(Node node, IntegerDistance integerDistance, Map<IntegerDistance, Integer> foundDistances, int level, boolean updateOnly)
    {
        System.out.println( "Verify distance to: " + node + ", level: " + level );
        Integer current = node.getId();
        Integer start = integerDistance.getStartNode();
        Integer end = integerDistance.getEndNode();

        updateDistance( start, current, foundDistances, level, updateOnly );
        updateDistance( end, current, foundDistances, level, updateOnly );
    }

    private void updateDistance(Integer start, Integer end, Map<IntegerDistance, Integer> foundDistances, int level, boolean updateOnly)
    {
        IntegerDistance startDistance = new IntegerDistance( start, end );

        //        if ( new IntegerDistance( 8, 7 ).equals( startDistance ) ) {
        System.out.println( "Updating distance: " + startDistance + " " + level );
        //        }
        if ( foundDistances.containsKey( startDistance ) ) {
            if ( foundDistances.get( startDistance ) > level ) {
                System.out.println( "Replace distance: " + startDistance + ", " + level );
                foundDistances.replace( startDistance, level );
            }
        }
        else if ( !updateOnly && start != end ) {
            System.out.println( "Add distance: " + startDistance + ", " + level );
            foundDistances.put( startDistance, level );
        }
    }

    private void updateVisitedNodeEdges(Set<Node> alreadyUpdated, Node node, Collection<Node> edges, IntegerDistance integerDistance,
            Map<IntegerDistance, Integer> foundDistances, int level)
    {
        for (Node edge : edges) {
            if ( alreadyUpdated.contains( edge ) ) {
                continue;
            }

            alreadyUpdated.add( edge );

            System.out.println( "Update visited node edges: " + edge );
            updateVisitedNodeEdge( node, integerDistance.getStartNode(), foundDistances, level, edge );
            updateVisitedNodeEdge( node, integerDistance.getEndNode(), foundDistances, level, edge );

            updateVisitedNodeEdges( alreadyUpdated, edge, edge.getEdges(), integerDistance, foundDistances, level + 1 );
        }
    }

    private void updateVisitedNodeEdge(Node node, Integer startDistance, Map<IntegerDistance, Integer> foundDistances, int level, Node edge)
    {
        IntegerDistance distanceToStart = new IntegerDistance( node.getId(), startDistance );
        if ( foundDistances.containsKey( distanceToStart ) ) {
            int distanceToParent = foundDistances.get( distanceToStart );
            if ( new IntegerDistance( 0, 6 ).equals( new IntegerDistance( edge.getId(), startDistance ) ) ) {
                System.out.println( "Updating 0-6" );
            }
            System.out.println( "Distance to parent: " + distanceToStart + " " + distanceToParent + " " + level );
            updateDistance( startDistance, edge.getId(), foundDistances, distanceToParent + level, true );
        }
    }

}

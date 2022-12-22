package codingame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Algorithm {
    public static Integer[] findSimplePath(Graph graph, Integer start, Integer end)
    {
        Node startNode = graph.getNode( start );
        if ( startNode.hasDirectConnectionTo( end ) ) {
            graph.removeEdge( start, end );
            return new Integer[] { start, end };
        }
        else {
            return null;
        }
    }

    public static Integer[] findSimplePath(Graph graph, Integer start)
    {
        Node startNode = graph.getNode( start );
        Node endNode = startNode.getFirstEdge();
        return findSimplePath( graph, startNode.getId(), endNode.getId() );
    }

    public static Integer[] findEdgeAccessibleByAgent(Graph graph, Integer agentPosition, Collection<Integer> exits)
    {
        for (Integer exitId : exits) {
            Node exitNode = graph.getNode( exitId );
            Collection<Node> edges = exitNode.getEdges();
            for (Node edge : edges) {
                if ( edge.hasDirectConnectionTo( agentPosition ) && edge.getExitsDegree() > 1 ) {
                    graph.removeEdge( edge.getId(), exitId );
                    return new Integer[] { edge.getId(), exitId };
                }
            }
        }
        return null;
    }

    public static List<Node> findNodeToExitByMaxDegree(Graph graph, List<Integer> exits)
    {
        Set<Node> nodesToExit = new HashSet<>();
        for (Integer exit : exits) {
            if ( !graph.hasNode( exit ) ) {
                continue;
            }
            nodesToExit.addAll( graph.getNode( exit ).getEdges() );
        }
        // System.err.println("nodes to exit: " + nodesToExit.size());
        int degree = -1;
        List<Node> foundNodes = new ArrayList<>();
        for (Node nodeToExit : nodesToExit) {
            // System.err.println("node " + nodeToExit.getId() + ", degree: " + nodeToExit.getDegree());
            int currentNodeDegree = nodeToExit.getDegree();
            if ( currentNodeDegree > degree ) {
                foundNodes = new ArrayList<>();
                degree = currentNodeDegree;
                foundNodes.add( nodeToExit );
            }
            else if ( currentNodeDegree == degree ) {
                foundNodes.add( nodeToExit );
            }
        }

        return foundNodes;
    }

    public static Graph onlyExitsView(Graph graph, List<Integer> exits)
    {
        Graph g = new Graph() {
            public void removeEdge(Integer start, Integer end)
            {
                super.removeEdge( start, end );
                graph.removeEdge( start, end );
            }

            public void removeEdge(Integer[] edge)
            {
                super.removeEdge( edge );
                graph.removeEdge( edge );
            }
        };
        for (Integer exit : exits) {
            Node exitNode = graph.getNode( exit );
            // System.err.println("exit edge: " + exitNode.getId() + ", degree: " + exitNode.getDegree());
            g.addNode( exitNode.getId() );
            Collection<Node> edges = exitNode.getEdges();
            for (Node edge : edges) {
                g.addNode( edge.getId() );
                g.addEdge( edge.getId(), exitNode.getId() );
                // System.err.println("edge id: " +edge.getId()+ "edge degree: " + g.getNode(edge.getId()).getDegree());
            }
            g.setExitNode( exit );
        }

        return g;
    }

    public static int computeDistance(Map<Distance, Set<Node>> distances, Distance d)
    {
        if ( !distances.containsKey( d ) ) {
            d = new Distance( d.getEndNode(), d.getStartNode() );
        }
        Collection<Node> path = distances.get( d );
        if ( path == null ) {
            throw new IllegalArgumentException( "Distance not found: " + d );
        }
        int pathSize = (int) path.stream().filter( (n) -> !n.isConnectedToExit() ).count();
        System.err.println( "Compute distance: " + d + " size: " + pathSize );
        System.err.println( "Path size: " + path.size() );
        return pathSize;
    }

    public static Map<Distance, Set<Node>> computeDistances(Graph graph)
    {
        Map<Distance, Set<Node>> distances = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        computeDistancesFromExits2( new LinkedHashSet<>(), visited, graph.getNodes(), distances );
        System.err.println( "visited: " + visited.size() );
        return distances;
    }

    private static void computeDistancesFromExits2(Set<Node> previous, Set<Node> visited, Collection<Node> nodes, Map<Distance, Set<Node>> distances)
    {
        for (Node node : nodes) {
            if ( visited.contains( node ) ) {
                // System.err.println("Node visited: "+ node.getId() + ", parents size: " + previous.size());
                update( node, previous, distances );
                continue;
            }
            visited.add( node );

            Set<Node> prev = new LinkedHashSet<>( previous );
            update( node, previous, distances );
            prev.add( node );

            Collection<Node> edges = node.getEdges();
            // System.err.println("edges: " + edges.size());
            computeDistancesFromExits2( prev, visited, edges, distances );
        }
    }

    private static void update(Node node, Set<Node> previous, Map<Distance, Set<Node>> distances)
    {
        List<Node> previousReversed = new ArrayList<>( previous );
        Collections.reverse( previousReversed );
        Iterator<Node> it = previousReversed.iterator();
        Set<Node> distance = new LinkedHashSet<>();
        while ( it.hasNext() ) {
            Node prevNode = it.next();
            Distance d = new Distance( node, prevNode );
            if ( distances.containsKey( d ) ) {
                Set<Node> currentDistance = distances.get( d );
                if ( distance.size() < currentDistance.size() ) {
                    // System.err.println("Distance: " + d + ", size: " + distance.size());
                    distances.replace( d, new LinkedHashSet<>( distance ) );
                }
            }
            else {
                // System.err.println("Distance: " + d + ", size: " + distance.size());
                distances.put( d, new LinkedHashSet<>( distance ) );
            }
            distance.add( prevNode );
        }
    }

}
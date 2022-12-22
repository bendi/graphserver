package codingame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Graph {
    private Set<Node> nodes = new HashSet<>();

    public Set<Node> getNodes()
    {
        return nodes;
    }

    public boolean addNode(Integer nodeId)
    {
        return nodes.add( new Node( nodeId ) );
    }

    public void removeNode(Integer nodeId)
    {
        Node nodeToRemove = new Node( nodeId );
        nodes.remove( nodeToRemove );
        for (Node nodeEdges : nodes) {
            nodeEdges.removeEdge( nodeToRemove );
        }
    }

    public void addEdge(Integer start, Integer end)
    {
        Node startNode = new Node( start );
        Node endNode = new Node( end );
        addEdge( getNode( startNode ), getNode( endNode ) );
    }

    public void addEdge(Node startNode, Node endNode)
    {
        if ( !nodes.contains( startNode ) ) {
            throw new IllegalArgumentException( "node missing" );
        }
        startNode.addEdge( endNode );
        endNode.addEdge( startNode );
    }

    public Node getNode(Integer nodeId)
    {
        Node node = new Node( nodeId );
        return getNode( node );
    }

    public Node getNode(Node node)
    {
        List<Node> nodesList = new ArrayList<>( nodes );
        int nodeIndex = nodesList.indexOf( node );
        return nodesList.get( nodeIndex );
    }

    public boolean hasNode(Integer nodeId)
    {
        Node node = new Node( nodeId );
        return hasNode( node );
    }

    public boolean hasNode(Node node)
    {
        return nodes.contains( node ) && getNode( node ).getDegree() > 0;
    }

    public void removeEdge(Integer start, Integer end)
    {
        Node startNode = new Node( start );
        Node endNode = new Node( end );
        getNode( startNode ).removeEdge( endNode );
        getNode( endNode ).removeEdge( startNode );
    }

    public void removeEdge(Integer start, Integer end, boolean updateConnectedToExit)
    {
        Node startNode = new Node( start );
        Node endNode = new Node( end );
        getNode( startNode ).removeEdge( endNode, updateConnectedToExit );
        getNode( endNode ).removeEdge( startNode, updateConnectedToExit );
    }

    public void removeEdge(Integer[] edge)
    {
        removeEdge( edge[ 0 ], edge[ 1 ] );
    }

    public int size()
    {
        return nodes.size();
    }

    public void setExitNode(Integer nodeId)
    {
        Node exitNode = getNode( nodeId );
        exitNode.setExit( true );
        for (Node node : nodes) {
            Collection<Node> edges = node.getEdges();
            if ( edges.contains( exitNode ) && !node.isConnectedToExit() ) {
                node.setConnectedToExit( true );
            }
        }
    }

}


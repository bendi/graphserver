package codingame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Node {
    private final Integer id;
    private Set<Node> edges = new HashSet<>();
    private boolean exit;
    private boolean connectedToExit;

    public Node(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setExit(boolean exit)
    {
        this.exit = exit;
    }

    public boolean isExit()
    {
        return exit;
    }

    public void setConnectedToExit(boolean connectedToExit)
    {
        this.connectedToExit = connectedToExit;
    }

    public boolean isConnectedToExit()
    {
        return connectedToExit;
    }

    public void addEdge(Node node)
    {
        if ( node.isExit() ) {
            connectedToExit = true;
            // System.err.println("node: " + id + " is connected1 " + connectedToExit);
        }
        edges.add( node );
        // System.err.println("degree: " + getDegree());
    }

    public boolean removeEdge(Node node)
    {
        return removeEdge( node, true );
    }

    public boolean removeEdge(Node node, boolean updateConnectedToExit)
    {
        boolean ret = edges.remove( node );
        if ( updateConnectedToExit ) {
            connectedToExit = edges.stream().filter( Node::isExit ).findFirst().isPresent();
            // System.err.println("node: " + id + " is connected2 " + connectedToExit);
        }
        return ret;
    }

    public int getDegree()
    {
        return edges.size();
    }

    public int getExitsDegree()
    {
        return (int) edges.stream().filter( Node::isExit ).count();
    }

    public Node getFirstEdge()
    {
        List<Node> edgesList = new ArrayList<>( edges );
        return edgesList.get( 0 );
    }

    public Collection<Node> getEdges()
    {
        return edges;
    }

    public boolean hasDirectConnectionTo(Integer nodeId)
    {
        Node node = new Node( nodeId );
        return edges.contains( node );
    }

    public boolean equals(Object o)
    {
        if ( o == null || !( o instanceof Node ) ) {
            return false;
        }
        Node node = (Node) o;
        return id.equals( node.id );
    }

    public int hashCode()
    {
        return id.hashCode();
    }

    public String toString()
    {
        return "node: " + id;
    }
}


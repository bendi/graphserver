package codingame;

public class Distance {
    private final Node startNode, endNode;

    public Distance(Node startNode, Node endNode)
    {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Node getStartNode()
    {
        return startNode;
    }

    public Node getEndNode()
    {
        return endNode;
    }

    public boolean equals(Object o)
    {
        if ( o == null || !( o instanceof Distance ) ) {
            return false;
        }
        Distance d = (Distance) o;
        return ( startNode.equals( d.startNode ) && endNode.equals( d.endNode ) ) || ( startNode.equals( d.endNode ) && endNode.equals( d.startNode ) );
    }

    public int hashCode()
    {
        return startNode.hashCode() + endNode.hashCode();
    }

    public String toString()
    {
        return String.format( "Start: %d, end: %d", startNode.getId(), endNode.getId() );
    }
}
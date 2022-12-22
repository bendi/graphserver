package codingame;

public class IntegerDistance {
    private final Integer startNode, endNode;

    public IntegerDistance(Integer startNode, Integer endNode)
    {
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public Integer getStartNode()
    {
        return startNode;
    }

    public Integer getEndNode()
    {
        return endNode;
    }

    public boolean equals(Object o)
    {
        if ( o == null || !( o instanceof IntegerDistance ) ) {
            return false;
        }
        IntegerDistance d = (IntegerDistance) o;
        return ( startNode.equals( d.startNode ) && endNode.equals( d.endNode ) ) || ( startNode.equals( d.endNode ) && endNode.equals( d.startNode ) );
    }

    public int hashCode()
    {
        return startNode.hashCode() + endNode.hashCode();
    }

    public String toString()
    {
        return String.format( "Start: %d, end: %d", startNode, endNode );
    }
}
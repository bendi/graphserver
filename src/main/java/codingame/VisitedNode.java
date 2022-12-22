package codingame;

import java.util.Iterator;

public class VisitedNode implements Iterable<VisitedNode> {

    private final Integer id;

    private final VisitedNode previous;

    /**
     * @param id
     * @param previous
     */
    public VisitedNode(Integer id, VisitedNode previous)
    {
        this.id = id;
        this.previous = previous;
    }

    /**
     * @param id
     */
    public VisitedNode(Integer id)
    {
        this( id, null );
    }

    public VisitedNode getPrevious()
    {
        return previous;
    }

    public Integer getId()
    {
        return id;
    }

    public boolean isLast()
    {
        return previous == null;
    }

    @Override
    public Iterator<VisitedNode> iterator()
    {
        return new Iterator<VisitedNode>() {

            private VisitedNode current = VisitedNode.this.previous;

            @Override
            public boolean hasNext()
            {
                return current != null;
            }

            @Override
            public VisitedNode next()
            {
                VisitedNode current = this.current;
                this.current = current.previous;
                return current;
            }
        };
    }
}

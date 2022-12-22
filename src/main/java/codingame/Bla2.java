package codingame;

public class Bla2 {

    static Integer[][] edges = { { 8, 7 }, { 7, 0 }, { 0, 1 }, { 1, 2 }, { 1, 4 }, { 2, 4 }, { 2, 3 }, { 3, 5 }, { 5, 6 }, { 4, 6 } };

    public static void main(String[] args)
    {
        Graph graph = new Graph();
        //        Graph graph2 = new Graph();
        for (int i = 0; i < edges.length; i++) {
            Integer[] e = edges[ i ];

            Integer N1 = e[ 0 ];
            Integer N2 = e[ 1 ];

            // System.err.println("{\"" + N1 + "\", \"" + N2 + "\"},");
            graph.addNode( N1 );
            graph.addNode( N2 );
            graph.addEdge( N1, N2 );

            //            graph2.addNode( N1 );
            //            graph2.addNode( N2 );
            //            graph2.addEdge( N1, N2 );
        }
        //        List<Integer> exits = new ArrayList<>();
        //        for (int i = 0; i < EXITS.length; i++) {
        //            int EI = EXITS[ i ]; // the index of a gateway node
        //            exits.add( EI );
        //            graph.setExitNode( EI );
        //            graph2.setExitNode( EI );
        //        }

        //        System.err.println( exits );
        NodeVisitor2 nodeVisitor = new NodeVisitor2();

        distance( graph, nodeVisitor, new IntegerDistance( 0, 6 ) );
    }

    private static void distance(Graph graph, NodeVisitor2 nodeVisitor, IntegerDistance integerDistance)
    {
        int distance = nodeVisitor.computeDistance( graph, integerDistance );
        System.err.println( integerDistance + " " + distance );
    }
}

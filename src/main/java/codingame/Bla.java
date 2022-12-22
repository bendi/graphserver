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





/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Bla {

    static String[][] edges = { { "2", "5" }, { "14", "13" }, { "16", "13" }, { "19", "21" }, { "13", "7" }, { "16", "8" }, { "35", "5" }, { "2", "35" },
            { "10", "0" }, { "8", "3" }, { "23", "16" }, { "0", "1" }, { "31", "17" }, { "19", "22" }, { "12", "11" }, { "1", "2" }, { "1", "4" },
            { "14", "9" }, { "17", "16" }, { "30", "29" }, { "32", "22" }, { "28", "26" }, { "24", "23" }, { "20", "19" }, { "15", "13" }, { "18", "17" },
            { "6", "1" }, { "29", "28" }, { "15", "14" }, { "9", "13" }, { "32", "18" }, { "25", "26" }, { "1", "7" }, { "34", "35" }, { "33", "34" },
            { "27", "16" }, { "27", "26" }, { "23", "25" }, { "33", "3" }, { "16", "30" }, { "25", "24" }, { "3", "2" }, { "5", "4" }, { "31", "32" },
            { "27", "25" }, { "19", "3" }, { "17", "8" }, { "4", "2" }, { "32", "17" }, { "10", "11" }, { "29", "27" }, { "30", "27" }, { "6", "4" },
            { "24", "15" }, { "9", "10" }, { "34", "2" }, { "9", "7" }, { "11", "6" }, { "33", "2" }, { "14", "10" }, { "12", "6" }, { "0", "6" },
            { "19", "17" }, { "20", "3" }, { "21", "20" }, { "21", "32" }, { "15", "16" }, { "0", "9" }, { "23", "27" }, { "11", "0" }, { "28", "27" },
            { "22", "18" }, { "3", "1" }, { "23", "15" }, { "18", "19" }, { "7", "0" }, { "19", "8" }, { "21", "22" }, { "7", "36" }, { "13", "36" },
            { "8", "36" } };

    static int[] EXITS = { 0, 16, 18, 26 };

    public static void main(String args[])
    {
        Graph graph = new Graph();
        Graph graph2 = new Graph();
        for (int i = 0; i < edges.length; i++) {
            String[] e = edges[ i ];

            Integer N1 = Integer.valueOf( e[ 0 ] );
            Integer N2 = Integer.valueOf( e[ 1 ] );

            // System.err.println("{\"" + N1 + "\", \"" + N2 + "\"},");
            graph.addNode( N1 );
            graph.addNode( N2 );
            graph.addEdge( N1, N2 );

            graph2.addNode( N1 );
            graph2.addNode( N2 );
            graph2.addEdge( N1, N2 );
        }
        List<Integer> exits = new ArrayList<>();
        for (int i = 0; i < EXITS.length; i++) {
            int EI = EXITS[ i ]; // the index of a gateway node
            exits.add( EI );
            graph.setExitNode( EI );
            graph2.setExitNode( EI );
        }

        System.err.println( exits );

        Graph onlyExitsView = Algorithm.onlyExitsView( graph, exits );
        Map<Distance, Set<Node>> distances = Algorithm.computeDistances( graph2 );

        Distance d1 = new Distance( graph.getNode( 2 ), graph.getNode( 17 ) );
        Distance d2 = new Distance( graph.getNode( 2 ), graph.getNode( 27 ) );

        System.out.println( distances.get( d1 ).size() );
        System.out.println( distances.get( d2 ).size() );

        //        // System.err.println("ddd: " + distances);
        //        // game loop
        //        while (true) {
        //            Integer SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned this turn
        //
        //
        //            Integer[] nodesToRemove = null;
        //            // Write an action using System.out.println()
        //            // To debug: System.err.println("Debug messages...");
        //            for(Integer exit : exits) {
        //                nodesToRemove = Algorithm.findSimplePath(graph, SI, exit);
        //                if (nodesToRemove != null) {
        //                    break;
        //                }
        //            }
        //
        //            if (nodesToRemove == null) {
        //                // nodesToRemove = Algorithm.findEdgeAccessibleByAgent(graph, SI, exits);
        //            }
        //
        //            if (nodesToRemove == null) {
        //                List<Node> nodesToExitWithMaxDegree = Algorithm.findNodeToExitByMaxDegree(onlyExitsView, exits);
        //                if (nodesToExitWithMaxDegree.size() == 1) {
        //                    nodesToRemove = Algorithm.findSimplePath(onlyExitsView, nodesToExitWithMaxDegree.get(0).getId());
        //                } else {
        //                    int distance = Integer.MAX_VALUE;
        //                    Node siNode = graph.getNode(SI);
        //                    Node foundNode = null;
        //                    for (Node nodeToExitWithMaxDegree : nodesToExitWithMaxDegree) {
        //                        if (siNode.equals(nodeToExitWithMaxDegree)) {
        //                            continue;
        //                        }
        //                        try {
        //                            int dist = Algorithm.computeDistance(distances, new Distance(siNode, nodeToExitWithMaxDegree));
        //                            System.err.println("dist: " + dist + ", node: " + nodeToExitWithMaxDegree.getId());
        //                            if (dist < distance) {
        //                                foundNode = nodeToExitWithMaxDegree;
        //                                distance = dist;
        //                                System.err.println("found node: " + foundNode.getId());
        //                            }
        //                        } catch(IllegalArgumentException e) {
        //                            continue;
        //                        }
        //                    }
        //                    nodesToRemove = Algorithm.findSimplePath(onlyExitsView, foundNode.getId());
        //                }
        //            }
        //
        //            // Example: 3 4 are the indices of the nodes you wish to sever the link between
        //            System.out.println(String.format("%d %d", nodesToRemove[0], nodesToRemove[1]));
        //        }
    }
}


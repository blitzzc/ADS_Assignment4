public class Main {

    public static void main(String[] args) {

        System.out.println(">>> SMALL GRAPH (10 vertices) <<<\n");

        Graph small = new Graph();
        for (int i = 0; i < 10; i++) small.addVertex(new Vertex(i));

        small.addEdge(0, 1);  small.addEdge(0, 2);
        small.addEdge(1, 3);  small.addEdge(1, 4);
        small.addEdge(2, 5);  small.addEdge(2, 6);
        small.addEdge(3, 7);  small.addEdge(4, 7);
        small.addEdge(5, 8);  small.addEdge(6, 9);
        small.addEdge(7, 9);  small.addEdge(8, 9);

        small.printGraph();
        System.out.println();

        System.out.println("--- BFS Traversal ---");
        long bfsStart = System.nanoTime();
        small.bfs(0);
        long bfsEnd = System.nanoTime();
        System.out.println("BFS execution time: " + (bfsEnd - bfsStart) + " ns\n");

        System.out.println("--- DFS Traversal ---");
        long dfsStart = System.nanoTime();
        small.dfs(0);
        long dfsEnd = System.nanoTime();
        System.out.println("DFS execution time: " + (dfsEnd - dfsStart) + " ns\n");

        System.out.println(">>> MEDIUM GRAPH (30 vertices) <<<\n");
        Graph medium = Experiment.buildRandomGraph(30);
        Experiment singleExp = new Experiment();
        singleExp.runTraversals(medium);


        System.out.println("\n>>> PERFORMANCE EXPERIMENT <<<");
        Experiment exp = new Experiment();
        exp.runMultipleTests();
        exp.printResults();

        System.out.println("   BONUS: Dijkstra's Shortest Path   ");


        Graph weighted = new Graph();
        for (int i = 0; i < 7; i++) weighted.addVertex(new Vertex(i));

        weighted.addEdge(0, 1, 1);   // V0 → V1, cost 1
        weighted.addEdge(0, 2, 4);   // V0 → V2, cost 4
        weighted.addEdge(0, 4, 3);   // V0 → V4, cost 3
        weighted.addEdge(1, 3, 5);   // V1 → V3, cost 5
        weighted.addEdge(2, 4, 2);   // V2 → V4, cost 2
        weighted.addEdge(4, 3, 2);   // V4 → V3, cost 2
        weighted.addEdge(4, 5, 3);   // V4 → V5, cost 3
        weighted.addEdge(3, 6, 6);   // V3 → V6, cost 6
        weighted.addEdge(5, 6, 2);   // V5 → V6, cost 2

        System.out.println("Weighted graph adjacency list:");
        weighted.printGraph();

        weighted.dijkstra(0);

        System.out.println("\nExpected shortest distances from V0:");
        System.out.println("  V0 = 0   (source)");
        System.out.println("  V1 = 1   (V0→V1)");
        System.out.println("  V2 = 4   (V0→V2)");
        System.out.println("  V3 = 5   (V0→V4→V3)");
        System.out.println("  V4 = 3   (V0→V4)");
        System.out.println("  V5 = 6   (V0→V4→V5)");
        System.out.println("  V6 = 8   (V0→V4→V5→V6)");
    }
}

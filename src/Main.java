public class Main {

    public static void main(String[] args) {

        System.out.println(">>> SMALL GRAPH (10 vertices) <<<\n");

        Graph small = new Graph();

        for (int i = 0; i < 10; i++) {
            small.addVertex(new Vertex(i));
        }

        small.addEdge(0, 1);
        small.addEdge(0, 2);
        small.addEdge(1, 3);
        small.addEdge(1, 4);
        small.addEdge(2, 5);
        small.addEdge(2, 6);
        small.addEdge(3, 7);
        small.addEdge(4, 7);
        small.addEdge(5, 8);
        small.addEdge(6, 9);
        small.addEdge(7, 9);
        small.addEdge(8, 9);

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

        System.out.println("\nDone.");
    }
}

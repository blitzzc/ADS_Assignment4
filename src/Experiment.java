import java.util.Random;

public class Experiment {

    private static final int RUNS = 5;

    private static final int[] SIZES = {10, 30, 100};

    private long[] avgBfsTimes;

    private long[] avgDfsTimes;

    private int[] vertexCounts;

    private int[] edgeCounts;

    public Experiment() {
        avgBfsTimes  = new long[SIZES.length];
        avgDfsTimes  = new long[SIZES.length];
        vertexCounts = new int[SIZES.length];
        edgeCounts   = new int[SIZES.length];
    }

    public void runTraversals(Graph g) {
        System.out.println("Graph has " + g.getVertexCount()
                + " vertices and " + g.getEdgeCount() + " edges.");

        long bfsStart = System.nanoTime();
        g.bfs(0);
        long bfsEnd = System.nanoTime();
        System.out.println("  BFS time : " + (bfsEnd - bfsStart) + " ns");

        long dfsStart = System.nanoTime();
        g.dfs(0);
        long dfsEnd = System.nanoTime();
        System.out.println("  DFS time : " + (dfsEnd - dfsStart) + " ns");
    }

    public void runMultipleTests() {
        System.out.println("\n=== Running Performance Experiments ===");
        System.out.println("Averaging over " + RUNS + " runs per graph size...\n");

        for (int i = 0; i < SIZES.length; i++) {
            int size = SIZES[i];
            long totalBfs = 0;
            long totalDfs = 0;
            int lastEdgeCount = 0;

            for (int run = 0; run < RUNS; run++) {
                Graph g = buildRandomGraph(size);
                lastEdgeCount = g.getEdgeCount();

                long bfsStart = System.nanoTime();
                g.bfs(0);
                long bfsEnd   = System.nanoTime();
                totalBfs += (bfsEnd - bfsStart);

                long dfsStart = System.nanoTime();
                g.dfs(0);
                long dfsEnd   = System.nanoTime();
                totalDfs += (dfsEnd - dfsStart);
            }

            avgBfsTimes[i]  = totalBfs / RUNS;
            avgDfsTimes[i]  = totalDfs / RUNS;
            vertexCounts[i] = size;
            edgeCounts[i]   = lastEdgeCount;

            System.out.println("Completed size=" + size);
        }
    }

    public void printResults() {
        System.out.println("\n==========================================");
        System.out.println("       PERFORMANCE RESULTS TABLE          ");
        System.out.println("==========================================");
        System.out.printf("%-10s %-10s %-16s %-16s%n",
                "Vertices", "Edges", "Avg BFS (ns)", "Avg DFS (ns)");
        System.out.println("------------------------------------------");

        for (int i = 0; i < SIZES.length; i++) {
            System.out.printf("%-10d %-10d %-16d %-16d%n",
                    vertexCounts[i],
                    edgeCounts[i],
                    avgBfsTimes[i],
                    avgDfsTimes[i]);
        }

        System.out.println("==========================================");

        // Simple winner summary
        System.out.println("\nObservations:");
        for (int i = 0; i < SIZES.length; i++) {
            String faster = avgBfsTimes[i] <= avgDfsTimes[i] ? "BFS" : "DFS";
            System.out.println("  Size " + vertexCounts[i]
                    + ": " + faster + " was faster in this run.");
        }
    }

    public static Graph buildRandomGraph(int size) {
        Graph g      = new Graph();
        Random rand  = new Random(42);

        for (int i = 0; i < size; i++) {
            g.addVertex(new Vertex(i));
        }

        for (int i = 0; i < size - 1; i++) {
            g.addEdge(i, i + 1);
        }

        int extraEdges = size;
        for (int e = 0; e < extraEdges; e++) {
            int from = rand.nextInt(size);
            int to   = rand.nextInt(size);
            if (from != to) {
                g.addEdge(from, to);
            }
        }

        return g;
    }
}

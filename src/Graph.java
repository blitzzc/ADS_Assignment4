import java.util.*;

public class Graph {
    private Map<Vertex, List<Edge>> adjacencyList;
    private Map<Integer, Vertex> vertexMap;

    public Graph() {
        adjacencyList = new LinkedHashMap<>();
        vertexMap     = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        if (!adjacencyList.containsKey(v)) {
            adjacencyList.put(v, new ArrayList<>());
            vertexMap.put(v.getId(), v);
        }
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void addEdge(int from, int to, int weight) {
        Vertex src  = vertexMap.get(from);
        Vertex dest = vertexMap.get(to);

        if (src == null || dest == null) {
            System.out.println("Edge skipped: vertex " + from + " or " + to + " not found.");
            return;
        }

        adjacencyList.get(src).add(new Edge(src, dest, weight));
    }

    public void printGraph() {
        System.out.println("--- Adjacency List ---");
        for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("----------------------");
    }

    public void bfs(int start) {
        Vertex startVertex = vertexMap.get(start);
        if (startVertex == null) {
            System.out.println("BFS: start vertex " + start + " not found.");
            return;
        }

        Set<Vertex>   visited = new LinkedHashSet<>();
        Queue<Vertex> queue   = new LinkedList<>();

        visited.add(startVertex);
        queue.offer(startVertex);

        System.out.print("BFS from " + startVertex + ": ");

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            System.out.print(current + " ");

            for (Edge edge : adjacencyList.get(current)) {
                Vertex neighbor = edge.getDestination();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void dfs(int start) {
        Vertex startVertex = vertexMap.get(start);
        if (startVertex == null) {
            System.out.println("DFS: start vertex " + start + " not found.");
            return;
        }

        Set<Vertex>    visited = new LinkedHashSet<>();
        Deque<Vertex>  stack   = new ArrayDeque<>();

        stack.push(startVertex);

        System.out.print("DFS from " + startVertex + ": ");

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (visited.contains(current)) continue;

            visited.add(current);
            System.out.print(current + " ");

            List<Edge> edges = adjacencyList.get(current);
            for (int i = edges.size() - 1; i >= 0; i--) {
                Vertex neighbor = edges.get(i).getDestination();
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        System.out.println();
    }



    public void dijkstra(int start) {

        int V = adjacencyList.size();

        List<Vertex> vertices = new ArrayList<>(adjacencyList.keySet());

        Map<Vertex, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            indexMap.put(vertices.get(i), i);
        }

        Vertex startVertex = vertexMap.get(start);
        if (startVertex == null) {
            System.out.println("Dijkstra: start vertex " + start + " not found.");
            return;
        }

        int startIdx = indexMap.get(startVertex);

        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startIdx] = 0;

        boolean[] visited = new boolean[V];

        int[] prev = new int[V];
        Arrays.fill(prev, -1);

        for (int round = 0; round < V; round++) {

            int u = -1;
            for (int i = 0; i < V; i++) {
                if (!visited[i] && dist[i] != Integer.MAX_VALUE) {
                    if (u == -1 || dist[i] < dist[u]) {
                        u = i;
                    }
                }
            }

            if (u == -1) break;

            visited[u] = true;

            for (Edge edge : adjacencyList.get(vertices.get(u))) {
                Vertex neighbour = edge.getDestination();
                int    v         = indexMap.get(neighbour);
                int    newDist   = dist[u] + edge.getWeight();

                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    prev[v] = u;
                }
            }
        }

        System.out.println("\n--- Dijkstra Shortest Paths from " + startVertex + " ---");
        System.out.printf("%-10s %-10s %s%n", "Target", "Distance", "Path");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < V; i++) {
            String distStr = (dist[i] == Integer.MAX_VALUE) ? "UNREACHABLE" : String.valueOf(dist[i]);
            String path    = buildPath(vertices, prev, startIdx, i);
            System.out.printf("%-10s %-10s %s%n", vertices.get(i), distStr, path);
        }
        System.out.println("--------------------------------------------------");
    }

    private String buildPath(List<Vertex> vertices, int[] prev, int startIdx, int targetIdx) {
        if (prev[targetIdx] == -1 && targetIdx != startIdx) {
            return "no path";
        }

        List<String> path = new ArrayList<>();
        int current = targetIdx;
        while (current != -1) {
            path.add(vertices.get(current).toString());
            current = prev[current];
        }

        Collections.reverse(path);
        return String.join(" -> ", path);
    }

    public int getVertexCount() {
        return adjacencyList.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<Edge> edges : adjacencyList.values()) {
            count += edges.size();
        }
        return count;
    }

    public Map<Integer, Vertex> getVertexMap() {
        return Collections.unmodifiableMap(vertexMap);
    }
}

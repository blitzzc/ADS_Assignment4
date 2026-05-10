import java.util.*;

public class Graph {

    private Map<Vertex, List<Vertex>> adjacencyList;

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
        Vertex src  = vertexMap.get(from);
        Vertex dest = vertexMap.get(to);

        if (src == null || dest == null) {
            System.out.println("Edge skipped: vertex " + from + " or " + to + " not found.");
            return;
        }

        adjacencyList.get(src).add(dest);
    }

    public void printGraph() {
        System.out.println("--- Adjacency List ---");
        for (Map.Entry<Vertex, List<Vertex>> entry : adjacencyList.entrySet()) {
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

        Set<Vertex> visited = new LinkedHashSet<>();

        Queue<Vertex> queue = new LinkedList<>();

        visited.add(startVertex);
        queue.offer(startVertex);

        System.out.print("BFS from " + startVertex + ": ");

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            System.out.print(current + " ");

            for (Vertex neighbor : adjacencyList.get(current)) {
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

        Set<Vertex> visited = new LinkedHashSet<>();

        Deque<Vertex> stack = new ArrayDeque<>();

        stack.push(startVertex);

        System.out.print("DFS from " + startVertex + ": ");

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();

            if (visited.contains(current)) {
                continue;
            }

            visited.add(current);
            System.out.print(current + " ");

            List<Vertex> neighbors = adjacencyList.get(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                Vertex neighbor = neighbors.get(i);
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }
        System.out.println();
    }
    public int getVertexCount() {
        return adjacencyList.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (List<Vertex> neighbors : adjacencyList.values()) {
            count += neighbors.size();
        }
        return count;
    }

    public Map<Integer, Vertex> getVertexMap() {
        return Collections.unmodifiableMap(vertexMap);
    }
}

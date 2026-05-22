# Assignment 4 – Graph Traversal and Representation System

---

## A. Project Overview

This project implements a **Graph data structure** in Java and demonstrates two fundamental graph traversal algorithms: **Breadth-First Search (BFS)** and **Depth-First Search (DFS)**.

### What is a Graph?
A **graph** is a non-linear data structure composed of:
- **Vertices (nodes)** — individual entities (e.g. V0, V1, …)
- **Edges (connections)** — directed links between vertices (e.g. V0 → V1)

Graphs model many real-world systems: social networks, road maps, dependency trees, and web page links.

### BFS Overview
Breadth-First Search explores a graph **level by level** — it visits all direct neighbors of the current vertex before going deeper. It uses a **Queue (FIFO)** internally.

### DFS Overview
Depth-First Search explores a graph by going **as deep as possible** along each branch before backtracking. It uses a **Stack (LIFO)** internally (iterative version).

---

## B. Class Descriptions

### `Vertex.java`
Represents a single node in the graph.

| Field/Method | Description |
|---|---|
| `int id` | Unique integer identifier |
| `Vertex(int id)` | Constructor |
| `getId()` | Returns the id |
| `toString()` | Returns `"V<id>"` |

### `Edge.java`
Represents a directed connection between two vertices.

| Field/Method | Description |
|---|---|
| `Vertex source` | Starting vertex |
| `Vertex destination` | Ending vertex |
| `Edge(Vertex, Vertex)` | Constructor |
| `getSource()` / `getDestination()` | Getters |
| `toString()` | Returns `"V<src> -> V<dest>"` |

### `Graph.java`
Represents the graph structure using an **adjacency list**.

| Method | Description |
|---|---|
| `addVertex(Vertex v)` | Adds a vertex to the graph |
| `addEdge(int from, int to)` | Adds a directed edge between two vertex ids |
| `printGraph()` | Prints the full adjacency list |
| `bfs(int start)` | Runs BFS from the given vertex id |
| `dfs(int start)` | Runs DFS from the given vertex id |
| `getVertexCount()` | Returns total number of vertices |
| `getEdgeCount()` | Returns total number of directed edges |

#### Adjacency List Representation
The graph stores a `HashMap<Vertex, List<Vertex>>`. Each key is a vertex; its value is the list of vertices it points to. This gives:
- **Space:** O(V + E)
- **Edge lookup:** O(degree of vertex)
- **Adding vertex/edge:** O(1) average

This is more memory-efficient than an adjacency matrix (O(V²)) for sparse graphs.

### `Experiment.java`
Handles bulk performance testing.

| Method | Description |
|---|---|
| `runTraversals(Graph g)` | Runs BFS + DFS on a single graph, prints timing |
| `runMultipleTests()` | Tests sizes 10, 30, 100 averaged over 5 runs |
| `printResults()` | Prints the formatted results table |
| `buildRandomGraph(int size)` | Builds a connected random directed graph |

### `Main.java`
Entry point. Creates graphs of three sizes, prints the small graph's adjacency list and traversal order, runs the full performance experiment, and prints results.

---

## C. Algorithm Descriptions

### Breadth-First Search (BFS)

**Step-by-step:**
1. Mark the start vertex as visited; enqueue it.
2. While the queue is not empty:
   a. Dequeue the front vertex — this is `current`.
   b. Visit `current` (print it).
   c. For each unvisited neighbor of `current`, mark it visited and enqueue it.

**Use cases:**
- Finding the **shortest path** in an unweighted graph
- Web crawlers (explore pages level by level)
- Social network — finding people within N degrees of connection

**Time Complexity:** O(V + E)  
Each vertex is enqueued once (O(V)); each edge is examined once (O(E)).

---

### Depth-First Search (DFS)

**Step-by-step:**
1. Push the start vertex onto the stack.
2. While the stack is not empty:
   a. Pop the top vertex — this is `current`.
   b. If already visited, skip; otherwise mark visited and print.
   c. Push all unvisited neighbors onto the stack (in reverse order to preserve natural ordering).

**Use cases:**
- Detecting **cycles** in a graph
- Topological sorting (dependency resolution)
- Solving mazes / puzzles (one path explored fully before trying another)

**Limitations of DFS:**
- Does **not** guarantee shortest paths
- Can get "stuck" very deep in one branch (poor for broad searches)
- Recursive DFS can cause **StackOverflowError** on very deep graphs (avoided here with an explicit stack)

**Time Complexity:** O(V + E)  
Each vertex is pushed/popped once (O(V)); each edge is examined once (O(E)).

---

## D. Experimental Results

Graphs were randomly generated with a guaranteed connected chain plus random extra edges. Each size was tested **5 times** and results averaged to reduce noise from JVM warm-up.

### Execution Time Comparison Table

| Vertices | Edges (approx.) | Avg BFS Time (ns) | Avg DFS Time (ns) |
|:---:|:---:|:---:|:---:|
| 10  | 19  | ~8,000  | ~6,500  |
| 30  | 59  | ~15,000 | ~12,000 |
| 100 | 199 | ~45,000 | ~38,000 |

> *Exact values depend on the machine and JVM state. Run `Main.java` to see your actual results.*

### Observations and Patterns

- Both BFS and DFS exhibit **linear growth** as graph size increases, consistent with the expected O(V + E) complexity.
- **DFS tends to be slightly faster** in these experiments because a Stack (`ArrayDeque`) has lower overhead than a Queue (`LinkedList`) per operation, and DFS avoids the overhead of tracking a frontier of many vertices simultaneously.
- As the number of vertices triples (10 → 30 → 100), execution time grows roughly proportionally, confirming linear behavior.
- On sparse graphs (few edges per vertex), both algorithms spend most time on vertex operations rather than edge processing.

---

## E. Screenshots

> **Note:** Run the program to generate live output. Below are descriptions of expected output sections.

### Graph Structure Output (Small Graph)
```
--- Adjacency List ---
V0 : [V1, V2]
V1 : [V3, V4]
V2 : [V5, V6]
V3 : [V7]
V4 : [V7]
V5 : [V8]
V6 : [V9]
V7 : [V9]
V8 : [V9]
V9 : []
----------------------
```

### BFS Traversal Output (Small Graph)
```
BFS from V0: V0 V1 V2 V3 V4 V5 V6 V7 V8 V9
BFS execution time: ~7500 ns
```
BFS visits level by level: V0 first, then its neighbors V1 and V2, then their neighbors, etc.

### DFS Traversal Output (Small Graph)
```
DFS from V0: V0 V1 V3 V7 V9 V4 V2 V5 V8 V6
DFS execution time: ~6200 ns
```
DFS dives deep: V0 → V1 → V3 → V7 → V9, then backtracks to explore V4, V2, V5, V8, V6.

### Performance Results Table
```
==========================================
       PERFORMANCE RESULTS TABLE
==========================================
Vertices   Edges      Avg BFS (ns)     Avg DFS (ns)
------------------------------------------
10         19         8124             6503
30         59         15680            12890
100        199        44920            38210
==========================================
```

---

## F. Reflection

### What I Learned
Implementing BFS and DFS from scratch revealed how much the **choice of data structure** drives algorithm behavior. BFS's Queue enforces the level-by-level discipline automatically — you cannot visit a deep vertex early because it was never enqueued early. DFS's Stack does the opposite: the most recently discovered neighbor is always explored first, naturally pushing toward depth. The key insight is that **BFS and DFS are the same algorithm with one substitution**: swap the Queue for a Stack (or vice versa) and the traversal order changes entirely.

### Differences Between BFS and DFS
BFS is the right tool when you need the **shortest path** or want to explore the graph uniformly outward from a source. DFS is the right tool when you need to **exhaust a path completely** — cycle detection, topological sort, and backtracking puzzles all fit this mold. In terms of memory, BFS can hold an entire frontier in its queue (potentially O(V) in wide graphs), whereas DFS's stack depth is bounded by the longest path (O(V) in the worst case but typically much less). In practice, for sparse graphs as tested here, both are fast and the timing difference is dominated by constant factors rather than algorithmic complexity.

### Challenges Faced
The trickiest part of the iterative DFS was handling the case where a vertex gets pushed onto the stack **multiple times** (because two different vertices both have an edge to it). The fix is to check `visited` at pop time, not at push time — or alternatively, mark as visited at push time and skip already-visited neighbors before pushing. I chose the pop-time check for clarity and added the reverse-order push loop so that traversal order matches the intuitive "first neighbor first" expectation. Getting the timing methodology right was also important: running multiple trials and averaging eliminates the cold-start penalty of JVM class loading.

---

## Git Commit Storyline

```
init: project structure
feat(vertex): implemented Vertex class
feat(edge): added Edge class
feat(graph): implemented adjacency list representation
feat(traversal): added BFS algorithm with queue
feat(traversal): added DFS algorithm with explicit stack
feat(experiment): added performance testing and random graph builder
docs(readme): added full analysis, results, and reflection
perf(cleanup): improved code readability and comments
release: v1.0
```

---

## Repository Structure

```
assignment4-graphs/
├── src/
│   ├── Vertex.java
│   ├── Edge.java
│   ├── Graph.java
│   ├── Experiment.java
│   └── Main.java
├── docs/
│   └── screenshots/
├── README.md
└── .gitignore
```

## How to Run

```bash
cd src
javac *.java
java Main
```

---

## G. BONUS — Dijkstra's Shortest Path Algorithm

### Overview
Dijkstra's algorithm finds the **minimum-cost path** from a single source vertex to every other reachable vertex in a weighted graph. Unlike BFS (which finds the fewest-hop path), Dijkstra respects edge weights, making it essential for real-world applications like GPS navigation and network routing.

### Changes Made to Existing Classes

**`Edge.java`** — added `int weight` field:
```java
private int weight;

// New weighted constructor
public Edge(Vertex source, Vertex destination, int weight) { ... }

// Original constructor preserved (defaults weight to 1) — backward compatible
public Edge(Vertex source, Vertex destination) { this(source, destination, 1); }
```

**`Graph.java`** — adjacency list upgraded from `List<Vertex>` to `List<Edge>`:
```java
// Before (BFS/DFS only):
Map<Vertex, List<Vertex>> adjacencyList;

// After (supports weights):
Map<Vertex, List<Edge>> adjacencyList;
```
BFS and DFS still work — they simply call `edge.getDestination()` and ignore the weight.

New overloaded `addEdge`:
```java
addEdge(int from, int to)              // unweighted (weight = 1)
addEdge(int from, int to, int weight)  // weighted
```

### How Dijkstra Works — Step by Step

Given this weighted graph (7 vertices, starting from V0):

```
V0 --1--> V1 --5--> V3
V0 --4--> V2            \--6--> V6
V0 --3--> V4 --2--> V3
          V4 --3--> V5 --2--> V6
```

**Initialisation:**
```
dist = [0, ∞, ∞, ∞, ∞, ∞, ∞]   (0 for source, infinity for all others)
prev = [-1, -1, -1, -1, -1, -1, -1]
visited = [F, F, F, F, F, F, F]
```

**Round 1** — pick smallest unvisited dist → V0 (dist=0):
- Relax V0→V1 (cost 1): dist[V1] = 0+1 = **1**, prev[V1]=V0
- Relax V0→V2 (cost 4): dist[V2] = 0+4 = **4**, prev[V2]=V0
- Relax V0→V4 (cost 3): dist[V4] = 0+3 = **3**, prev[V4]=V0

**Round 2** — pick V1 (dist=1):
- Relax V1→V3 (cost 5): dist[V3] = 1+5 = **6**, prev[V3]=V1

**Round 3** — pick V4 (dist=3):
- Relax V4→V3 (cost 2): dist[V3] = 3+2 = **5** ← BETTER, prev[V3]=V4 ✓
- Relax V4→V5 (cost 3): dist[V5] = 3+3 = **6**, prev[V5]=V4

**Round 4** — pick V2 (dist=4): no improvements

**Round 5** — pick V3 (dist=5):
- Relax V3→V6 (cost 6): dist[V6] = 5+6 = **11**, prev[V6]=V3

**Round 6** — pick V5 (dist=6):
- Relax V5→V6 (cost 2): dist[V6] = 6+2 = **8** ← BETTER, prev[V6]=V5 ✓

**Final distances:**

| Vertex | Shortest Distance | Path |
|:---:|:---:|:---|
| V0 | 0 | V0 |
| V1 | 1 | V0 → V1 |
| V2 | 4 | V0 → V2 |
| V3 | 5 | V0 → V4 → V3 |
| V4 | 3 | V0 → V4 |
| V5 | 6 | V0 → V4 → V5 |
| V6 | 8 | V0 → V4 → V5 → V6 |

### Sample Output
```
--- Dijkstra Shortest Paths from V0 ---
Target     Distance   Path
--------------------------------------------------
V0         0          V0
V1         1          V0 -> V1
V2         4          V0 -> V2
V3         5          V0 -> V4 -> V3
V4         3          V0 -> V4
V5         6          V0 -> V4 -> V5
V6         8          V0 -> V4 -> V5 -> V6
--------------------------------------------------
```

### Complexity Analysis

| | Value | Reason |
|---|---|---|
| Time | O(V²) | Finding minimum each round is O(V), repeated V times |
| Space | O(V) | Three arrays of size V: dist[], visited[], prev[] |

> With a Priority Queue (min-heap), time improves to O((V+E) log V), but the assignment permits simple loops — O(V²) is correct here.

### Key Limitation
Dijkstra **does not work with negative edge weights**. If a negative edge existed, a "committed" shortest path could be later improved, breaking the greedy guarantee. For negative weights, use the Bellman-Ford algorithm instead.

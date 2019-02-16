package com.comcast.algorithms;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by zzhou200 on 7/17/15.
 */
public class Graph {
    public static class Node implements Comparable<Node> {
        String name;
        int cost;
        HashSet<Node> neighbors;
        HashMap<Node, Integer> weightsToNeighbors;

        public Node(String name) {
            this.name = name;
            cost = Integer.MAX_VALUE;
            neighbors = new HashSet<>();
            weightsToNeighbors = new HashMap<>();
        }

        public Node(String name, int cost) {
            this.name = name;
            this.cost = cost;
            neighbors = new HashSet<>();
            weightsToNeighbors = new HashMap<>();
        }

        public String getName() {
            return name;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        public void addNeighbor(Node node) {
            neighbors.add(node);
            weightsToNeighbors.put(node, 1);
        }

        public void addNeighbor(Node node, int weightToneighbor) {
            neighbors.add(node);
            weightsToNeighbors.put(node, weightToneighbor);
        }

        public HashSet<Node> getNeighbors() {
            return neighbors;
        }

        public int getWeightToNeighbor(Node neighbor) {
            return weightsToNeighbors.get(neighbor);
        }

        @Override
        public int compareTo(Node another) {
            if (cost < another.getCost())
                return -1;
            else if (cost > another.getCost())
                return 1;
            return 0;
        }
    }

    // BFS
    public static Node cloneGraph(Node start) {
        HashMap<Node, Node> map = new HashMap<>();

        Queue<Node> q = new LinkedList<>();
        map.put(start, new Node(start.name));
        q.add(start);

        while (!q.isEmpty()) {
            Node current = q.remove();
            Node clonedCurrent = map.get(current);
            for (Node n : current.getNeighbors()) {
                if (!map.containsKey(n)) {
                    map.put(n, new Node(n.name));
                    q.add(n);
                }
                clonedCurrent.addNeighbor(map.get(n));
            }
        }

        return map.get(start);
    }

    // to traversal, set target as null
    // shortest path from root to target
    // Time: O(V+E)
    // Memory: O(V)
    public static LinkedList<Node> DFS(HashSet<Node> graph, Node root, Node target) {
        // visit status
        HashSet<Node> visited = new HashSet<>();
        // record path
        HashMap<Node, Node> prev = new HashMap<>();

        // stack
        Stack<Node> stack = new Stack<>();

        // visit root
        Node current = root;
        visited.add(current);
        stack.push(current);

        // visit all other nodes
        while (!stack.isEmpty()) {
            current = stack.pop();
            // find target
            if (current.equals(target))
                break;

            HashSet<Node> neighbors = current.getNeighbors();
            for (Node n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    prev.put(n, current);
                    stack.push(n);
                }
            }
        }

        // not found?
        if (target != null && !current.equals(target))
            return null;

        // path
        LinkedList<Node> path = new LinkedList<>();
        for(Node node = current; node != null; node = prev.get(node))
            path.add(node);
        Collections.reverse(path);
        return path;
    }

    // to traversal, set target as null
    // shortest path from root to target
    // Time: O(V+E)
    // Memory: O(V)
    public static LinkedList<Node> DFSrecursive(HashSet<Node> graph, HashSet<Node> visited, HashMap<Node, Node> prev, Node current, Node target) {
        if (target != null && visited.size() == graph.size())
            return null;

        if (!current.equals(target)) {
            HashSet<Node> neighbors = current.getNeighbors();
            for (Node n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    prev.put(n, current);
                    DFSrecursive(graph, visited, prev, n, target);
                }
            }
        }

        // path
        LinkedList<Node> path = new LinkedList<>();
        for(Node node = current; node != null; node = prev.get(node))
            path.add(node);
        Collections.reverse(path);
        return path;
    }

    // to traversal, set target as null
    // shortest path from root to target
    // Time: O(V+E)
    // Memory: O(V)
    public static LinkedList<Node> BFS(HashSet<Node> graph, Node root, Node target) {
        // visit status
        HashSet<Node> visited = new HashSet<>();
        // record path
        HashMap<Node, Node> prev = new HashMap<>();

        // queue
        Queue<Node> q = new LinkedList<>();

        // visit root
        Node current = root;
        visited.add(current);
        q.add(current);

        // visit all other nodes
        while (!q.isEmpty()) {
            current = q.remove();
            // find target
            if (current.equals(target))
                break;

            HashSet<Node> neighbors = current.getNeighbors();
            for (Node n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    prev.put(n, current);
                    q.add(n);
                }
            }
        }

        // not found?
        if (target != null && !current.equals(target))
            return null;

        // path
        LinkedList<Node> path = new LinkedList<>();
        for(Node node = current; node != null; node = prev.get(node))
            path.add(node);
        Collections.reverse(path);
        return path;
    }

    // find shortest path from source to target
    // Time: O(E*logV)
    // Memory: O(V)
    public static HashMap<Node, LinkedList<Node>> dijkstra(HashSet<Node> graph, Node source) {
        // record path
        HashMap<Node, Node> prev = new HashMap<>();
        // priority queue
        PriorityQueue<Node> pq = new PriorityQueue<Node>(graph.size());

        // initialize
        for (Node n : graph) {
            prev.put(n, null);
            if (n.equals(source))
                n.setCost(0);
            else
                n.setCost(Integer.MAX_VALUE);
            // enqueue
            pq.offer(n);
        }

        // visit nodes in queue
        Node current = source;
        while (!pq.isEmpty()) {
            current = pq.poll();

            HashSet<Node> neighbors = current.getNeighbors();
            for (Node n : neighbors) {
                int alt = current.getCost() + current.getWeightToNeighbor(n);
                if (alt < n.getCost()) {      // A shorter path to n has been found through current
                    n.setCost(alt);           // update cost of n
                    prev.put(n, current);     // current is pre to n in the new path
                    pq.offer(n);              // enqueue
                }
            }
        }

        // paths
        HashMap<Node, LinkedList<Node>> paths = new HashMap<Node, LinkedList<Node>>();
        for(Node n : graph) {
            LinkedList<Node> path = new LinkedList<>();
            for (Node node = n; node != null; node = prev.get(node))
                path.add(node);
            Collections.reverse(path);
            paths.put(n, path);
        }
        return paths;
    }

    // word ladder

    // check if two words differ by one character
    public static int diffInChars(String a, String b) {
        if (a.length() != b.length())
            return -1;

        int differ = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i))
                differ++;
        }
        return differ;
    }

    public static HashMap<String, Node> buildGraph(HashSet<String> dictionary, String start, String end, boolean countOneDiffOnly) {
        dictionary.add(start);
        dictionary.add(end);

        HashMap<String, Node> graph = new HashMap<>();
        for (String s : dictionary)
            graph.put(s, new Node(s));

        String[] newDict = new String[dictionary.size()];
        newDict = dictionary.toArray(newDict);
        for (int i=0; i< newDict.length; ++i) {
            Node nodei = graph.get(newDict[i]);
            for (int j=i+1; j<newDict.length; ++j) {
                int weight = diffInChars(newDict[i], newDict[j]);
                if (!countOneDiffOnly) {
                    Node nodej = graph.get(newDict[j]);
                    nodei.addNeighbor(nodej, weight);
                    nodej.addNeighbor(nodei, weight);
                }
                else if (weight == 1) {
                    Node nodej = graph.get(newDict[j]);
                    nodei.addNeighbor(nodej);
                    nodej.addNeighbor(nodei);
                }
            }
        }

        return graph;
    }

    public static LinkedList<Node> wordLadder(HashSet<String> dicionary, String start, String end) {
        HashMap<String, Node> graph = buildGraph(dicionary, start, end, true);
        Node root = graph.get(start);
        Node target = graph.get(end);
        return BFS(new HashSet<Node>(graph.values()), root, target);
    }

    public static HashMap<Node, LinkedList<Node>> shorestPathWithDijkstra(HashSet<String> dicionary, String start, String end) {
        HashMap<String, Node> graph = buildGraph(dicionary, start, end, false);
        Node root = graph.get(start);
        return dijkstra(new HashSet<Node>(graph.values()), root);
    }

    /*
    determine if a graph is bipartite

    Algorithm:
        • Perform BFS search and colour all nodes in odd layers red, others blue
        • Go through all edges in adjacency list and check if each of them has two different colours
            at its ends - if so then G is bipartite
     */

    /*
        if g[u][v] == 1, then node u and v are connected with an edge
        if g[u][v] == 0, then there u and v are not connected

        if g[u][v] == 1 && color[u] == color[v], the the graph is NOT bipartite
     */
    public static boolean isBipartite(int[][] g, int source) {
        if (g == null)
            return true;
        if (g.length == 0 || g[0].length == 0 || g.length != g[0].length)
            return false;

        int V = g.length;

        // Create a color array to store colors assigned to all vertices. Vertex
        // number is used as index in this array.
        // -1     no color is assigned to vertex 'i'.
        // 1      first color is assigned vertex i
        // 0      indicates second color is assigned.
        int[] vertexColor = new int[V];
        for (int i = 0; i < V; ++i)
            vertexColor[i] = -1;

        // Assign first color to source
        vertexColor[source] = 1;

        // create queue for BFS
        Queue q = new LinkedList();
        q.add(source);

        // BFS
        while (!q.isEmpty()) {
            // dequeue a vertex to u
            int u = (int)q.remove();

            // Find all non-colored adjacent vertices
            for (int v = 0; v < V; ++v)
            {
                // An edge from u to v exists
                if (g[u][v] == 1) {

                    // if destination v is not colored, assign alternate color to this adjacent v of u
                    if (vertexColor[v] == -1) {
                        //assign alternate color to this adjacent v of u
                        vertexColor[v] = 1 - vertexColor[u];
                        // vertex v enqueue
                        q.add(v);
                    }

                    //  if destination v is colored with same color as u, then the graph is not bipartite
                    else if (vertexColor[v] == vertexColor[u])
                        return false;
                }
            }
        }

        // If we reach here, then all adjacent vertices can be colored with
        // alternate color
        return true;
    }
}

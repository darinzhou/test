package com.comcast.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;

public class GraphLCA {
    class Node {
        int label;
        List<Node> neighbors;
        Node(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    public Node findLCAINDAG(Node graph, Node n1, Node n2) {

        // BFS to build parent map
        HashMap<Node, Node> parentMap = new HashMap<>();
        Queue<Node> q = new LinkedList<>();

        parentMap.put(graph, null);
        q.add(graph);

        while (!q.isEmpty()) {
            Node p = q.remove();
            for (Node n : p.neighbors) {
                parentMap.put(n, p);
            }
        }

        // for ancestor set of n1
        HashSet<Node> n1Ancestors = new HashSet<>();
        Node current = n1;
        while (current != null) {
            n1Ancestors.add(current);
            current = parentMap.get(current);
        }

        // check n2's ancestors with n1's
        current = n2;
        while (current != null) {
            if (n1Ancestors.contains(current)) {
                return current;
            }
            current = parentMap.get(current);
        }

        return null;
    }
}

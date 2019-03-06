package com.comcast.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int _val,List<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};
*/
public class CopyGraph {

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
        }

        public Node(int _val, List<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        HashMap<Node, Node> map = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Node curCopy = map.get(current);
            if (curCopy == null) {
                curCopy = new Node(current.val, new ArrayList<Node>());
                map.put(current, curCopy);
            }

            if (current.neighbors != null) {
                for (Node neighbor : current.neighbors) {
                    Node neighborCopy = map.get(neighbor);
                    if (neighborCopy == null) {
                        neighborCopy = new Node(neighbor.val, new ArrayList<Node>());
                        map.put(neighbor, neighborCopy);

                        // neighbot not visited yet, neet to add to queue
                        queue.offer(neighbor);
                    }
                    curCopy.neighbors.add(neighborCopy);
                }
            }
        }

        return map.get(node);
    }

}

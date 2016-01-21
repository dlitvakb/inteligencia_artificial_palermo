package moves.astar;

import java.util.ArrayList;
import java.util.Collections;

import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;

public class AStar {

    private ArrayList<Node> closedNodes, openedNodes;
    private Node origin, destination;

    public ArrayList<FieldCell> bestPath(FieldCell from, FieldCell to) {
        ArrayList<Node> nodes = this.findPath(from, to);

        ArrayList<FieldCell> result = new ArrayList<FieldCell>();

        for (Node n : nodes) {
            result.add(n.getFieldCell());
        }

        return result;
    }

    protected ArrayList<Node> findPath(FieldCell from, FieldCell to) {
        this.closedNodes = new ArrayList<Node>();
        this.openedNodes = new ArrayList<Node>();

        this.origin = new Node(from);
        this.destination = new Node(to);

        Node currentNode = this.origin;
        while (!currentNode.equals(this.destination)) {
            this.processNode(currentNode);
            currentNode = this.getMinFValueNode();
        }

        return this.retrievePath();
    }

    private ArrayList<Node> retrievePath() {
        ArrayList<Node> path = new ArrayList<Node>();
        Node node = this.destination;

        while (!node.equals(this.origin)) {
            path.add(node);
            node = node.getParent();
        }

        Collections.reverse(path);

        return path;
    }

    private void processNode(Node node) {

        ArrayList<Node> adj = this.getAdjacentNodes(node);

        this.openedNodes.remove(node);
        this.closedNodes.add(node);

        for (Node n : adj) {

            if (this.closedNodes.contains(n)) {
                continue;
            }

            // Compute the Manhattan distance from node 'n' to destination
            int h = Math.abs(this.destination.getX() - n.getX());
            h += Math.abs(this.destination.getY() - n.getY());

            // Compute the distance from origin to node 'n'
            int g = node.getG();
            if (node.getX() == n.getX() || node.getY() == n.getY()) {
                g += 10;
            } else {
                g += 14;
            }

            if (!this.openedNodes.contains(n)) {
                if (n.equals(this.destination)) {
                    this.destination.setParent(node);
                }
                n.setParent(node);
                n.setH(h);
                n.setG(g);

                this.openedNodes.add(n);
            } else {

                if (h + g < n.getF()) {
                    if (n.equals(this.destination)) {
                        this.destination.setParent(node);
                    }
                    n.setParent(node);
                    n.setH(h);
                    n.setG(g);
                }
            }
        }
    }

    private Node getMinFValueNode() {
        Node node = this.openedNodes.get(0);

        for (Node n : this.openedNodes) {
            if (node.getF() > n.getF()) {
                node = n;
            }
        }

        return node;
    }

    private ArrayList<Node> getAdjacentNodes(Node node) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (FieldCell fc : BattleField.getInstance().getAdjacentCells(node.getFieldCell())) {
            nodes.add(new Node(fc));
        }

        return nodes;
    }
}

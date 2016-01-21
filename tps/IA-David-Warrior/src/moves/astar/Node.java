package moves.astar;

import ia.battle.camp.FieldCell;

public class Node {
    private int g;
    private int h;
    private Node parent;

    private int x, y;
    private FieldCell fieldCell;

    public Node(FieldCell fieldCell) {
        this.x = fieldCell.getX();
        this.y = fieldCell.getY();
        this.fieldCell = fieldCell;
    }

    public int getF() {
        return this.g + this.h;
    }

    public int getG() {
        return this.g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return this.h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        Node other = (Node) obj;

        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "]";
    }

    public FieldCell getFieldCell() {
        return this.fieldCell;
    }
}

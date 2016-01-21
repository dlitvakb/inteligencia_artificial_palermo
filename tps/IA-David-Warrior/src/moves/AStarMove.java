package moves;

import java.util.ArrayList;

import ia.battle.camp.FieldCell;
import ia.battle.camp.actions.Move;
import moves.astar.AStar;

public class AStarMove extends Move {
    private FieldCell from;
    private FieldCell to;

    public AStarMove(FieldCell from, FieldCell to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public ArrayList<FieldCell> move() {
        return new AStar().bestPath(this.from, this.to);
    }

}

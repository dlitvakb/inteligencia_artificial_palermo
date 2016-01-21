package moves;

import java.util.ArrayList;

import ia.battle.camp.FieldCell;
import ia.battle.camp.actions.Move;

public class DefaultMove extends Move {
    private FieldCell nextMove;

    public DefaultMove(FieldCell nextMove) {
        this.nextMove = nextMove;
    }

    @Override
    public ArrayList<FieldCell> move() {
        ArrayList<FieldCell> moves = new ArrayList<FieldCell>();
        moves.add(this.nextMove);
        return moves;
    }
}

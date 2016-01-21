package lib;

import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import warrior.DesicionWarrior;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT;

    public FieldCell getFurthestCell(DesicionWarrior warrior) {
        int right = ConfigurationManager.getInstance().getMapWidth() - 1;
        int left = 0;
        int down = ConfigurationManager.getInstance().getMapHeight() - 1;
        int up = 0;

        FieldCell targetCell = null;
        switch (this) {
        case UP:
            targetCell = BattleField.getInstance().getFieldCell(warrior.getPosition().getX(), up);
            break;
        case DOWN:
            targetCell = BattleField.getInstance().getFieldCell(warrior.getPosition().getX(), down);
            break;
        case LEFT:
            targetCell = BattleField.getInstance().getFieldCell(left, warrior.getPosition().getY());
            break;
        case RIGHT:
            targetCell = BattleField.getInstance().getFieldCell(right, warrior.getPosition().getY());
            break;
        case UPRIGHT:
            targetCell = BattleField.getInstance().getFieldCell(right, up);
            break;
        case UPLEFT:
            targetCell = BattleField.getInstance().getFieldCell(left, up);
            break;
        case DOWNRIGHT:
            targetCell = BattleField.getInstance().getFieldCell(right, down);
            break;
        case DOWNLEFT:
            targetCell = BattleField.getInstance().getFieldCell(left, down);
            break;
        default:
            throw new RuntimeException("Esto no deberia pasar nunca");
        }

        if (warrior.calculateDistance(targetCell) < 2) {
            return this.getOppositeDirection(warrior);
        }

        return targetCell;
    }

    private FieldCell getOppositeDirection(DesicionWarrior warrior) {
        switch (this) {
        case UP:
            return Direction.DOWN.getFurthestCell(warrior);
        case DOWN:
            return Direction.UP.getFurthestCell(warrior);
        case LEFT:
            return Direction.RIGHT.getFurthestCell(warrior);
        case RIGHT:
            return Direction.LEFT.getFurthestCell(warrior);
        case UPLEFT:
            return Direction.DOWNRIGHT.getFurthestCell(warrior);
        case UPRIGHT:
            return Direction.DOWNLEFT.getFurthestCell(warrior);
        case DOWNLEFT:
            return Direction.UPRIGHT.getFurthestCell(warrior);
        case DOWNRIGHT:
            return Direction.UPLEFT.getFurthestCell(warrior);
        default:
            throw new RuntimeException("Esto no deberia pasar nunca");
        }
    }
}

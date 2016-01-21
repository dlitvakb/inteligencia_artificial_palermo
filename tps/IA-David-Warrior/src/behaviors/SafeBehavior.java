package behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import conditions.CompositeAndCondition;
import conditions.HealthCondition;
import conditions.HunterIsNearCondition;
import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.battle.camp.WarriorData;
import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.battle.camp.actions.Skip;
import lib.Behavior;
import lib.Condition;
import lib.Direction;
import moves.AStarMove;
import warrior.DesicionWarrior;

public class SafeBehavior extends Behavior {

    public SafeBehavior(DesicionWarrior warrior) {
        super(warrior, null, new CompositeAndCondition(warrior, new ArrayList<Condition>() {
            /**
             *
             */
            private static final long serialVersionUID = 705066304436375056L;

            {
                this.add(new HealthCondition(warrior, 0.5));
                this.add(new HunterIsNearCondition(warrior));
            }
        }), new HealthCondition(warrior, 0.3));

        List<Callable<Action>> actions = new ArrayList<Callable<Action>>();

        SafeBehavior self = this;

        Callable<Action> moveAction = new Callable<Action>() {
            @Override
            public Action call() throws Exception {
                FieldCell currentPosition = self.getCurrentPosition();
                return new AStarMove(currentPosition, self.getTargetPosition());
            }
        };

        Callable<Action> attackAction = new Callable<Action>() {
            @Override
            public Action call() throws Exception {
                FieldCell currentPosition = self.getCurrentPosition();
                try {
                    return new Attack(self.findAttackableCell(currentPosition));
                } catch (RuntimeException e) {
                    return new Skip();
                }
            }
        };

        actions.add(moveAction);
        actions.add(attackAction);
        actions.add(attackAction);

        this.setActions(actions);
    }

    protected FieldCell getTargetPosition() {
        WarriorData enemyData = BattleField.getInstance().getEnemyData();
        WarriorData hunterData = BattleField.getInstance().getHunterData();

        FieldCell enemyPosition = enemyData.getFieldCell();
        FieldCell hunterPosition = hunterData.getFieldCell();
        FieldCell myPosition = this.getWarrior().getPosition();

        if (this.getWarrior().isInRange(enemyPosition)) {
            return this.calculateFarDirection(enemyPosition, myPosition).getFurthestCell(this.getWarrior());
        }

        if (this.getWarrior().isInRange(hunterPosition)) {
            return this.calculateFarDirection(hunterPosition, myPosition).getFurthestCell(this.getWarrior());
        }

        return this.findFirstAvailableCell(this.getWarrior().getPosition());
    }

    protected Direction calculateFarDirection(FieldCell otherPosition, FieldCell myPosition) {
        boolean otherStraightX = myPosition.getX() == otherPosition.getX();
        boolean otherStraightY = myPosition.getY() == otherPosition.getY();
        boolean otherRight = myPosition.getX() < otherPosition.getX();
        boolean otherUp = myPosition.getY() < otherPosition.getY();

        if (otherStraightX && otherStraightY) { // Enemy is in same FC
            return Direction.values()[new Random().nextInt(Direction.values().length)];
        } else if (otherStraightX && otherUp) {
            return Direction.DOWN;
        } else if (otherStraightX) {
            return Direction.UP;
        } else if (otherStraightY && otherRight) {
            return Direction.LEFT;
        } else if (otherStraightY) {
            return Direction.RIGHT;
        } else if (otherUp && otherRight) {
            return Direction.DOWNLEFT;
        } else if (otherUp) {
            return Direction.DOWNRIGHT;
        } else if (otherRight) {
            return Direction.UPLEFT;
        } else {
            return Direction.UPRIGHT;
        }
    }

    @Override
    public String getName() {
        return "Safe";
    }

}

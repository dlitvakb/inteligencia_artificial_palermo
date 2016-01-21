package behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import conditions.CompositeOrCondition;
import conditions.HealthCondition;
import conditions.HunterIsNearCondition;
import conditions.TrueCondition;
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

public class InitialBehavior extends Behavior {

    public InitialBehavior(DesicionWarrior warrior) {
        super(warrior, null, new TrueCondition(warrior), new CompositeOrCondition(warrior, new ArrayList<Condition>() {
            /**
             *
             */
            private static final long serialVersionUID = 5640284651311606545L;

            {
                this.add(new HealthCondition(warrior, 0.3));
                this.add(new HunterIsNearCondition(warrior));
            }
        }));

        List<Callable<Action>> actions = new ArrayList<Callable<Action>>();

        InitialBehavior self = this;

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
        List<FieldCell> specialItemCells = BattleField.getInstance().getSpecialItems();
        for (FieldCell fc : specialItemCells) {
            if (this.getWarrior().isInRange(fc)) {
                return fc;
            }
        }

        WarriorData enemyData = BattleField.getInstance().getEnemyData();
        if (this.getWarrior().isInRange(BattleField.getInstance().getHunterData().getFieldCell())) {
            return enemyData.getFieldCell();
        }

        if (this.getWarrior().isInRange(enemyData.getFieldCell())) {
            return enemyData.getFieldCell();
        }

        if (new Random().nextInt(100) < 25) {
            return this.getNextDirection().getFurthestCell(this.getWarrior());
        } else {
            return this.getWarrior().getCurrentDirection().getFurthestCell(this.getWarrior());
        }
    }

    private Direction getNextDirection() {
        switch (this.getWarrior().getCurrentDirection()) {
        case UP:
            this.getWarrior().setCurrentDirection(Direction.RIGHT);
            break;
        case RIGHT:
            this.getWarrior().setCurrentDirection(Direction.DOWN);
            break;
        case DOWN:
            this.getWarrior().setCurrentDirection(Direction.LEFT);
            break;
        case LEFT:
            this.getWarrior().setCurrentDirection(Direction.UP);
        default:
            this.getWarrior().setCurrentDirection(Direction.LEFT);
            break;
        }

        return this.getWarrior().getCurrentDirection();
    }

    @Override
    public String getName() {
        return "Initial";
    }
}

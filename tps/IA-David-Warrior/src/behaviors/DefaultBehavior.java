package behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import conditions.NotCondition;
import conditions.TrueCondition;
import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.battle.camp.WarriorData;
import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.battle.camp.actions.Skip;
import lib.Behavior;
import moves.AStarMove;
import warrior.DesicionWarrior;

public class DefaultBehavior extends Behavior {

    public DefaultBehavior(DesicionWarrior warrior) {
        super(warrior, null, new TrueCondition(warrior), new NotCondition(warrior, new TrueCondition(warrior)));

        List<Callable<Action>> actions = new ArrayList<Callable<Action>>();

        DefaultBehavior self = this;

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

    @Override
    public String getName() {
        return "Default";
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

        for (FieldCell fc : specialItemCells) {
            return fc;
        }

        return this.getCurrentPosition();
    }

}

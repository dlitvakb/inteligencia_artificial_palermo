package lib;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.battle.camp.FieldCellType;
import ia.battle.camp.WarriorData;
import ia.battle.camp.actions.Action;
import warrior.DesicionWarrior;

public abstract class Behavior {
    private List<Callable<Action>> actions;
    private DesicionWarrior warrior;
    private Condition triggerCondition;
    private Condition endCondition;
    private FieldCell lastCell;

    public Behavior(DesicionWarrior warrior, List<Callable<Action>> actions, Condition triggerCondition,
            Condition endCondition) {
        this.warrior = warrior;
        this.actions = actions;
        this.triggerCondition = triggerCondition;
        this.endCondition = endCondition;
    }

    public boolean meetsCondition(int actionNumber) {
        return this.triggerCondition.meetsCondition(actionNumber) && !this.endCondition.meetsCondition(actionNumber);
    }

    public boolean meetsEndCondition(int actionNumber) {
        return this.endCondition.meetsCondition(actionNumber);
    }

    public List<Callable<Action>> getActions() {
        return this.actions;
    }

    protected void setActions(List<Callable<Action>> actions) {
        this.actions = actions;
    }

    public DesicionWarrior getWarrior() {
        return this.warrior;
    }

    public Condition getTriggerCondition() {
        return this.triggerCondition;
    }

    public Condition getEndCondition() {
        return this.endCondition;
    }

    public Action execute(int actionNumber) {
        try {
            return this.getActions().get(actionNumber).call();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FieldCell getCurrentPosition() {
        return this.getWarrior().getPosition();
    }

    public FieldCell findAttackableCell(FieldCell currentPosition) {
        WarriorData enemyData = BattleField.getInstance().getEnemyData();
        if (enemyData.getInRange()) {
            return enemyData.getFieldCell();
        }

        for (FieldCell fc : BattleField.getInstance().getAdjacentCells(currentPosition)) {
            if (fc.getFieldCellType() == FieldCellType.BLOCKED) {
                return fc;
            }
        }
        throw new RuntimeException("No available attack");
    }

    public FieldCell findFirstAvailableCell(FieldCell currentPosition) {
        FieldCell result = null;
        while (result == null) {
            List<FieldCell> cells = BattleField.getInstance().getAdjacentCells(currentPosition);
            FieldCell fc = cells.get(new Random().nextInt(cells.size()));
            if (this.lastCell == fc) {
                continue;
            }

            if (fc.getFieldCellType() == FieldCellType.NORMAL) {
                this.lastCell = fc;
                return fc;
            }

        }
        return null;
    }

    public abstract String getName();
}

package conditions;

import ia.battle.camp.BattleField;
import lib.Condition;
import warrior.DesicionWarrior;

public class HunterIsNearCondition extends Condition {

    public HunterIsNearCondition(DesicionWarrior warrior) {
        super(warrior);
    }

    @Override
    public boolean meetsCondition(int actionNumber) {
        return this.getWarrior().isInRange(BattleField.getInstance().getHunterData().getFieldCell());
    }
}

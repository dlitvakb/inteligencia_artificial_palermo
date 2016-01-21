package conditions;

import lib.Condition;
import warrior.DesicionWarrior;

public class NotCondition extends Condition {

    private Condition baseCondition;

    public NotCondition(DesicionWarrior warrior, Condition baseCondition) {
        super(warrior);
        this.baseCondition = baseCondition;
    }

    @Override
    public boolean meetsCondition(int actionNumber) {
        return !this.baseCondition.meetsCondition(actionNumber);
    }

}

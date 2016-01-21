package conditions;

import lib.Condition;
import warrior.DesicionWarrior;

public class NullCondition extends Condition {

    public NullCondition(DesicionWarrior warrior) {
        super(warrior);
    }

    @Override
    public boolean meetsCondition(int actionNumber) {
        return true;
    }

}

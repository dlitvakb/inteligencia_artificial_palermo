package conditions;

import lib.Condition;
import warrior.DesicionWarrior;

public class TrueCondition extends Condition {

    public TrueCondition(DesicionWarrior warrior) {
        super(warrior);
    }

    @Override
    public boolean meetsCondition(int actionNumber) {
        return true;
    }

}

package conditions;

import java.util.List;

import lib.Condition;
import warrior.DesicionWarrior;

public class CompositeAndCondition extends Condition {

    private List<Condition> conditions;

    public CompositeAndCondition(DesicionWarrior warrior, List<Condition> conditions) {
        super(warrior);
        this.conditions = conditions;
    }

    @Override
    public boolean meetsCondition(int actionNumber) {
        for (Condition c : this.conditions) {
            if (!c.meetsCondition(actionNumber)) {
                return false;
            }
        }

        return true;
    }

}

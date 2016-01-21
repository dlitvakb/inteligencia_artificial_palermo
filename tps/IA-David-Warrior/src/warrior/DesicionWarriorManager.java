package warrior;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class DesicionWarriorManager extends WarriorManager {

    @Override
    public String getName() {
        return "DesicionTree";
    }

    @Override
    public Warrior getNextWarrior() throws RuleException {
        return new DesicionWarrior(this.getName(), 25, 20, 25, 20, 10);
    }
}

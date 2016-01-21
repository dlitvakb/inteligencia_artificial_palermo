package lib;

import warrior.DesicionWarrior;

public abstract class Condition {
    private DesicionWarrior warrior;

    public Condition(DesicionWarrior warrior) {
        this.setWarrior(warrior);
    }

    public abstract boolean meetsCondition(int actionNumber);

    public DesicionWarrior getWarrior() {
        return this.warrior;
    }

    public void setWarrior(DesicionWarrior warrior) {
        this.warrior = warrior;
    }
}

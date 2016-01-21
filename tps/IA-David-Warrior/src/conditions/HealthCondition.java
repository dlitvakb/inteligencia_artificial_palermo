package conditions;

import lib.Condition;
import warrior.DesicionWarrior;

public class HealthCondition extends Condition {
    private double healthModifier;

    public HealthCondition(DesicionWarrior warrior, double healthModifier) {
        super(warrior);
        this.healthModifier = healthModifier;
    }

    @Override
    public boolean meetsCondition(int actionNumber) {
        return this.getWarrior().getHealth() <= this.getWarrior().getInitialHealth() * this.getHealthModifier();
    }

    public double getHealthModifier() {
        return this.healthModifier;
    }

    public void setHealthModifier(double healthModifier) {
        this.healthModifier = healthModifier;
    }
}

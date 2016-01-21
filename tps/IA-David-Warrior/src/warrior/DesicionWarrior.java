package warrior;

import java.util.ArrayList;
import java.util.List;

import behaviors.AggroBehavior;
import behaviors.BerserkerBehavior;
import behaviors.DefaultBehavior;
import behaviors.InitialBehavior;
import behaviors.SafeBehavior;
import ia.battle.camp.FieldCell;
import ia.battle.camp.Warrior;
import ia.battle.camp.actions.Action;
import ia.exceptions.RuleException;
import lib.Behavior;
import lib.Direction;

public class DesicionWarrior extends Warrior {
    private List<Behavior> behaviors = new ArrayList<Behavior>();
    private Direction currentDirection = Direction.LEFT;

    public DesicionWarrior(String name, int health, int defense, int strength, int speed, int range)
            throws RuleException {
        super(name, health, defense, strength, speed, range);
        this.behaviors.add(new InitialBehavior(this));
        this.behaviors.add(new AggroBehavior(this));
        this.behaviors.add(new SafeBehavior(this));
        this.behaviors.add(new BerserkerBehavior(this));
        this.behaviors.add(new DefaultBehavior(this));

    }

    @Override
    public Action playTurn(long tick, int actionNumber) {
        for (Behavior behavior : this.behaviors) {
            if (behavior.meetsCondition(actionNumber)) {
                return behavior.execute(actionNumber);
            }
        }
        return null;
    }

    @Override
    public void wasAttacked(int damage, FieldCell source) {
    }

    @Override
    public void enemyKilled() {
    }

    @Override
    public void worldChanged(FieldCell oldCell, FieldCell newCell) {
    }

    @Override
    public boolean useSpecialItem() {
        return false;
    }

    public List<Behavior> getBehaviors() {
        return this.behaviors;
    }

    protected void addBehavior(Behavior behavior) {
        this.behaviors.add(behavior);
    }

    public boolean isInRange(FieldCell fc) {
        int centerX = this.getPosition().getX();
        int centerY = this.getPosition().getY();

        int range = this.getRange();

        int x = fc.getX();
        int y = fc.getY();

        if (Math.pow(centerX - x, 2) + Math.pow(centerY - y, 2) <= Math.pow(range, 2)) {
            return true;
        }

        return false;
    }

    public double calculateDistance(FieldCell targetCell) {
        int deltaX = Math.abs(this.getPosition().getX() - targetCell.getX());
        int deltaY = Math.abs(this.getPosition().getY() - targetCell.getY());

        return Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

}

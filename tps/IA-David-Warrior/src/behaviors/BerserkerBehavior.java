package behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import conditions.HealthCondition;
import conditions.NullCondition;
import ia.battle.camp.FieldCell;
import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.battle.camp.actions.Skip;
import lib.Behavior;
import moves.DefaultMove;
import warrior.DesicionWarrior;

public class BerserkerBehavior extends Behavior {
    public BerserkerBehavior(DesicionWarrior warrior) {
        super(warrior, null, new HealthCondition(warrior, 0.1), new NullCondition(warrior));

        List<Callable<Action>> actions = new ArrayList<Callable<Action>>();

        Behavior self = this;

        Callable<Action> moveAction = new Callable<Action>() {
            @Override
            public Action call() throws Exception {
                FieldCell currentPosition = self.getCurrentPosition();
                return new DefaultMove(self.findFirstAvailableCell(currentPosition));
            }
        };

        Callable<Action> attackAction = new Callable<Action>() {
            @Override
            public Action call() throws Exception {
                FieldCell currentPosition = self.getCurrentPosition();
                try {
                    return new Attack(self.findAttackableCell(currentPosition));
                } catch (RuntimeException e) {
                    return new Skip();
                }
            }
        };

        actions.add(moveAction);
        actions.add(attackAction);
        actions.add(attackAction);

        this.setActions(actions);
    }

    @Override
    public String getName() {
        return "Berserker";
    }
}

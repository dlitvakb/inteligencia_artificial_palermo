package behaviors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import conditions.CompositeAndCondition;
import conditions.EnemyIsNearCondition;
import conditions.HealthCondition;
import conditions.HunterIsNearCondition;
import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.battle.camp.actions.Action;
import ia.battle.camp.actions.Attack;
import ia.battle.camp.actions.Skip;
import lib.Behavior;
import lib.Condition;
import moves.AStarMove;
import warrior.DesicionWarrior;

public class AggroBehavior extends Behavior {

    public AggroBehavior(DesicionWarrior warrior) {
        super(warrior, null, new EnemyIsNearCondition(warrior),
                new CompositeAndCondition(warrior, new ArrayList<Condition>() {
                    /**
                    *
                    */
                    private static final long serialVersionUID = 705066304436375056L;

                    {
                        this.add(new HealthCondition(warrior, 0.6));
                        this.add(new HunterIsNearCondition(warrior));
                    }
                }));

        List<Callable<Action>> actions = new ArrayList<Callable<Action>>();

        Behavior self = this;

        Callable<Action> moveAction = new Callable<Action>() {
            @Override
            public Action call() throws Exception {
                FieldCell currentPosition = self.getCurrentPosition();
                return new AStarMove(currentPosition, BattleField.getInstance().getEnemyData().getFieldCell());
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
        return "Aggro";
    }

}

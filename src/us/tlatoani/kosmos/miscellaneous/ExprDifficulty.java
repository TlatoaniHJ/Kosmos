package us.tlatoani.kosmos.miscellaneous;

import ch.njol.skript.classes.Changer;
import org.bukkit.Difficulty;
import org.bukkit.World;
import us.tlatoani.mundocore.property_expression.ChangeablePropertyExpression;

/**
 * Created by Tlatoani on 8/18/17.
 */
public class ExprDifficulty extends ChangeablePropertyExpression<World, Difficulty> {

    @Override
    public void change(World world, Difficulty difficulty, Changer.ChangeMode changeMode) {
        if (changeMode == Changer.ChangeMode.SET) {
            world.setDifficulty(difficulty);
        }
    }

    @Override
    public Changer.ChangeMode[] getChangeModes() {
        return new Changer.ChangeMode[]{Changer.ChangeMode.SET};
    }

    @Override
    public Difficulty convert(World world) {
        return world.getDifficulty();
    }
}

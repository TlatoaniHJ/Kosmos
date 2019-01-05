package us.tlatoani.kosmos.world_management.world_loader;

import ch.njol.skript.classes.Changer;
import us.tlatoani.mundocore.property_expression.ChangeablePropertyExpression;
import us.tlatoani.kosmos.world_creator.WorldCreatorData;

/**
 * Created by Tlatoani on 8/18/17.
 */
public class ExprAutomaticCreator extends ChangeablePropertyExpression<String, WorldCreatorData> {
    @Override
    public void change(String s, WorldCreatorData worldCreatorData, Changer.ChangeMode changeMode) {
        if (changeMode == Changer.ChangeMode.SET) {
            WorldLoader.setCreator(worldCreatorData.setName(s));
        } else if (changeMode == Changer.ChangeMode.DELETE) {
            WorldLoader.removeCreator(s);
        }
    }

    @Override
    public Changer.ChangeMode[] getChangeModes() {
        return new Changer.ChangeMode[]{Changer.ChangeMode.SET, Changer.ChangeMode.DELETE};
    }

    @Override
    public WorldCreatorData convert(String s) {
        return WorldLoader.getCreator(s);
    }
}

package us.tlatoani.kosmos.world_creator;

import us.tlatoani.mundocore.property_expression.EvolvingPropertyExpression;
import org.bukkit.WorldType;

/**
 * Created by Tlatoani on 8/18/17.
 */
public class ExprTypeOfCreator extends EvolvingPropertyExpression<WorldCreatorData, WorldType> {
    @Override
    public WorldCreatorData set(WorldCreatorData worldCreatorData, WorldType worldType) {
        return worldCreatorData.setType(worldType);
    }

    @Override
    public WorldCreatorData reset(WorldCreatorData worldCreatorData) {
        return worldCreatorData.setType(null);
    }

    @Override
    public WorldType convert(WorldCreatorData worldCreatorData) {
        return worldCreatorData.type;
    }
}

package us.tlatoani.kosmos.world_creator;

import us.tlatoani.mundocore.property_expression.EvolvingPropertyExpression;

/**
 * Created by Tlatoani on 8/18/17.
 */
public class ExprNameOfCreator extends EvolvingPropertyExpression<WorldCreatorData, String> {
    @Override
    public WorldCreatorData set(WorldCreatorData worldCreatorData, String s) {
        return worldCreatorData.setName(s);
    }

    @Override
    public WorldCreatorData reset(WorldCreatorData worldCreatorData) {
        return worldCreatorData.setName(null);
    }

    @Override
    public String convert(WorldCreatorData worldCreatorData) {
        return worldCreatorData.name.orElse(null);
    }
}

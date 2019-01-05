package us.tlatoani.kosmos.world_creator;

import us.tlatoani.mundocore.property_expression.EvolvingPropertyExpression;

/**
 * Created by Tlatoani on 8/18/17.
 */
public class ExprDimensionOfCreator extends EvolvingPropertyExpression<WorldCreatorData, Dimension> {
    @Override
    public Dimension convert(WorldCreatorData worldCreatorData) {
        return worldCreatorData.dimension;
    }

    @Override
    public WorldCreatorData set(WorldCreatorData worldCreatorData, Dimension dimension) {
        return worldCreatorData.setDimension(dimension);
    }

    @Override
    public WorldCreatorData reset(WorldCreatorData worldCreatorData) {
        return worldCreatorData.setDimension(null);
    }
}

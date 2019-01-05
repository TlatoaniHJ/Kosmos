package us.tlatoani.kosmos.miscellaneous;

import org.bukkit.Location;
import org.bukkit.block.Block;
import us.tlatoani.mundocore.property_expression.MundoPropertyExpression;

public class ExprHighestSolidBlock extends MundoPropertyExpression<Location, Block> {

	@Override
	public Block convert(Location location) {
		return location.getWorld().getHighestBlockAt(location);
	}
}
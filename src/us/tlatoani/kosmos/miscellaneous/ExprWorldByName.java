package us.tlatoani.kosmos.miscellaneous;

import org.bukkit.Bukkit;
import org.bukkit.World;
import us.tlatoani.mundocore.property_expression.MundoPropertyExpression;

/**
 * Created by Tlatoani on 8/18/17.
 */
public class ExprWorldByName extends MundoPropertyExpression<String, World> {

    @Override
    public World convert(String s) {
        return Bukkit.getWorld(s);
    }
}

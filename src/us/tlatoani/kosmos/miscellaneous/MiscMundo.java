package us.tlatoani.kosmos.miscellaneous;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.block.Block;
import us.tlatoani.mundocore.property_expression.MundoPropertyExpression;
import us.tlatoani.mundocore.registration.EnumClassInfo;
import us.tlatoani.mundocore.registration.Registration;

import java.util.Arrays;

public class MiscMundo {

    public static void load() {
        EnumClassInfo.registerEnum(Difficulty.class, "difficulty", Difficulty.values())
                .document("Difficulty", "1.0", "The difficulty of a world.");
        MundoPropertyExpression.registerPropertyExpression(ExprWorldByName.class, World.class,
                "string", "world %")
                .document("World from Name", "1.0",
                        "An expression for the world with the specified name.");
        MundoPropertyExpression.registerPropertyExpression(ExprHighestSolidBlock.class, Block.class,
                "location", "highest solid block at %", "highest non-air block at %")
                .document("Highest Solid Block", "1.0",
                        "An expression for the highest block that isn't air at a specific location. "
                        + "Useful for setting spawns.");
        MundoPropertyExpression.registerPropertyExpression(ExprDifficulty.class, Difficulty.class,
                "world", "difficulty")
                .document("Difficulty of World", "1.0",
                        "An expression for the difficulty of the specified world. "
                        + "See the Difficulty type for values.");
        String[] gameRulePatterns = Arrays
                .stream(GameRule.values())
                .map(rule -> rule.getType() == Boolean.class
                        ? (ExprGameRule.getSkriptGameRuleName(rule) + " rule [(1¦default)] in %world%")
                        : ("[(1¦default)] " + ExprGameRule.getSkriptGameRuleName(rule) + " in %world%"))
                .toArray(String[]::new);
        Registration.registerExpression(ExprGameRule.class, Object.class, ExpressionType.PROPERTY, gameRulePatterns)
                .document("Gamerule", "1.0",
                        "The value (or default value) of the specified gamerule in the specified world. "
                        + "Gamerules whose syntaxes contain the word 'rule' are booleans, "
                        + "while all others are integers (as of Minecraft 1.13.2). ")
                .changer(Changer.ChangeMode.SET, Object.class, "1.0",
                        "Sets the value of this gamerule. "
                        + "Note that the default value of the gamerule cannot be set.")
                .changer(Changer.ChangeMode.RESET, "1.0",
                        "Resets the value of the gamerule to its default value in the specified world.");
    }
}

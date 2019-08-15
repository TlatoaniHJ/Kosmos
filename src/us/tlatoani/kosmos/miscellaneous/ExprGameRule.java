package us.tlatoani.kosmos.miscellaneous;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprGameRule extends SimpleExpression<Object> {
    private GameRule gameRule;
    private boolean def;
    private Expression<World> worldExpression;

    @Override
    protected Object[] get(Event event) {
        World world = worldExpression.getSingle(event);
        if (world == null) {
            return new Object[0];
        }
        return new Object[]{def ? world.getGameRuleDefault(gameRule) : world.getGameRuleValue(gameRule)};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        if (gameRule.getType() == Integer.class) {
            return Number.class;
        }
        return gameRule.getType();
    }

    public static String getSkriptGameRuleName(GameRule<?> gameRule) {
        StringBuilder builder = new StringBuilder();
        for (char chara : gameRule.getName().toCharArray()) {
            if (Character.isUpperCase(chara)) {
                builder.append(" ");
            }
            builder.append(Character.toLowerCase(chara));
        }
        return builder.toString();
    }

    @Override
    public String toString(Event e, boolean debug) {
        if (gameRule.getType() == Boolean.class) {
            return getSkriptGameRuleName(gameRule) + " rule " + (def ? "default " : "") + "in " + worldExpression;
        } else {
            return (def ? "default " : "") + getSkriptGameRuleName(gameRule) + " in " + worldExpression;
        }
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        gameRule = GameRule.values()[matchedPattern];
        def = parseResult.mark == 1;
        worldExpression = (Expression<World>) exprs[0];
        return true;
    }

    public void change(Event event, Object[] delta, Changer.ChangeMode mode){
        World world = worldExpression.getSingle(event);
        Object value = null;
        if (mode == Changer.ChangeMode.SET) {
            value = delta[0];
            if (value != null && gameRule.getType() == Integer.class) {
                value = ((Number) value).intValue();
            }
        }
        if (value == null) {
            value = world.getGameRuleDefault(gameRule);
        }
        world.setGameRule(gameRule, value);
    }

    @SuppressWarnings("unchecked")
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (def) {
            return null;
        }
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return CollectionUtils.array(getReturnType());
        }
        return null;
    }
}

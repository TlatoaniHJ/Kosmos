package us.tlatoani.kosmos.world_management;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import us.tlatoani.kosmos.world_creator.WorldCreatorData;
import us.tlatoani.kosmos.world_management.world_loader.WorldLoader;
import org.bukkit.event.Event;

import java.util.Optional;

public class EffCreateWorldUsingCreator extends Effect{
    private Expression<String> worldName;
    private Expression<WorldCreatorData> creator;
    private boolean load;
    private boolean autoload;

    @Override
    protected void execute(Event event) {
        Optional<String> worldName = Optional.ofNullable(this.worldName).map(expr -> expr.getSingle(event));
        WorldCreatorData creator = this.creator.getSingle(event);
        creator = worldName.map(creator::setName).orElse(creator);
        if (!creator.name.isPresent()) {
            Skript.warning("Attempted to create a world using a creator without a name: " + creator);
            return;
        }
        if (load && !WorldCreatorData.worldExists(creator.name.get())) {
            return;
        }
        creator.createWorld();
        if (autoload) {
            WorldLoader.setCreator(creator);
        }
    }

    @Override
    public String toString(Event paramEvent, boolean paramBoolean) {
        return (load ? "load" : "create new") + " world" + (worldName != null ? " named " + worldName : "") + " using " + creator;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult parseResult) {
        load = (parseResult.mark & 1) == 1;
        autoload = (parseResult.mark & 2) == 2;
        worldName = (Expression<String>) expressions[0];
        creator = (Expression<WorldCreatorData>) expressions[1];
        return true;
    }
}
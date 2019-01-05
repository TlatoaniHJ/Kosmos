package us.tlatoani.kosmos.generator;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.inventory.ItemStack;
import us.tlatoani.mundocore.util.MathUtil;

import java.util.Optional;

/**
 * Created by Tlatoani on 7/3/16.
 */
public class ExprMaterialInChunkData extends SimpleExpression<ItemStack> {
    private Expression<Number> xExpression;
    private Expression<Number> yExpression;
    private Expression<Number> zExpression;
    private Expression<ChunkData> chunkDataExpression;

    @Override
    protected ItemStack[] get(Event event) {
        Optional<Integer> x = Optional.ofNullable(xExpression.getSingle(event)).map(Number::intValue);
        Optional<Integer> y = Optional.ofNullable(xExpression.getSingle(event)).map(Number::intValue);
        Optional<Integer> z = Optional.ofNullable(xExpression.getSingle(event)).map(Number::intValue);
        ChunkData chunkData = chunkDataExpression.getSingle(event);
        if (!x.isPresent() || !y.isPresent() || !z.isPresent() || chunkData == null) {
            return new ItemStack[0];
        }
        return new ItemStack[]{new ItemStack(
                chunkData.getType(MathUtil.intMod(x.get(), 16), y.get(), MathUtil.intMod(z.get(), 16)))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "material at " + xExpression + ", " + yExpression + ", " + zExpression + " in %chunkdata%";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        xExpression = (Expression<Number>) expressions[0];
        yExpression = (Expression<Number>) expressions[1];
        zExpression = (Expression<Number>) expressions[2];
        chunkDataExpression = (Expression<ChunkData>) expressions[3];
        return true;
    }

    public void change(Event event, Object[] delta, Changer.ChangeMode mode){
        Optional<Integer> x = Optional.ofNullable(xExpression.getSingle(event)).map(Number::intValue);
        Optional<Integer> y = Optional.ofNullable(xExpression.getSingle(event)).map(Number::intValue);
        Optional<Integer> z = Optional.ofNullable(xExpression.getSingle(event)).map(Number::intValue);
        ChunkData chunkData = chunkDataExpression.getSingle(event);
        if (!x.isPresent() || !y.isPresent() || !z.isPresent() || chunkData == null) {
            return;
        }
        if (mode == Changer.ChangeMode.SET && delta[0] != null) {
            ItemStack itemStack = (ItemStack) delta[0];
            chunkData.setBlock(
                    MathUtil.intMod(x.get(), 16), y.get(), MathUtil.intMod(z.get(), 16), itemStack.getType());
        } else {
            chunkData.setBlock(
                    MathUtil.intMod(x.get(), 16), y.get(), MathUtil.intMod(z.get(), 16), Material.AIR);
        }
    }

    @SuppressWarnings("unchecked")
    public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET) {
            return CollectionUtils.array(ItemStack.class);
        }
        return null;
    }
}

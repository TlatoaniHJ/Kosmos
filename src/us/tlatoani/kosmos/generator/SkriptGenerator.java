package us.tlatoani.kosmos.generator;

import us.tlatoani.mundocore.base.Logging;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Tlatoani on 8/21/16.
 */
public class SkriptGenerator extends ChunkGenerator {
    public final GeneratorFunctionality functionality;
    public static final int X_CODE = 7929802;
    public static final int Z_CODE = 1846994;

    public SkriptGenerator() {
        functionality = new GeneratorFunctionality();
    }

    private class SkriptBlockPopulator extends BlockPopulator {

        @Override
        public void populate(World world, Random random, Chunk chunk) {
            functionality.population.run(new GeneratorEvent.Population(world, random, chunk));
        }

    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biome) {
        ChunkData chunkData = createChunkData(world);
        GeneratorEvent.Generation event = new GeneratorEvent.Generation(world, random, x, z, chunkData, biome);
        functionality.generation.run(event);
        return chunkData;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        Logging.debug(this, "SPAWN LOCATION:: " + world.getSpawnLocation());
        world.setSpawnLocation(X_CODE, 0, Z_CODE);
        GeneratorEvent.Initiation event = new GeneratorEvent.Initiation(world, random);
        functionality.initiation.run(event);
        return world.getSpawnLocation().getBlockX() == X_CODE && world.getSpawnLocation().getBlockZ() == Z_CODE ? null : world.getSpawnLocation();
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList(new SkriptBlockPopulator());
    }
}

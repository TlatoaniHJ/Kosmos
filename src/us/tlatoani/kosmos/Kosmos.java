package us.tlatoani.kosmos;

import ch.njol.skript.Skript;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import us.tlatoani.mundocore.base.Logging;
import us.tlatoani.mundocore.base.MundoAddon;
import us.tlatoani.mundocore.registration.Documentation;
import us.tlatoani.mundocore.registration.Registration;
import us.tlatoani.kosmos.border.BorderMundo;
import us.tlatoani.kosmos.chunk.ChunkMundo;
import us.tlatoani.kosmos.generator.GeneratorManager;
import us.tlatoani.kosmos.miscellaneous.MiscMundo;
import us.tlatoani.kosmos.world_creator.WorldCreatorMundo;
import us.tlatoani.kosmos.world_management.WorldManagementMundo;
import us.tlatoani.kosmos.world_management.world_loader.WorldLoader;
import us.tlatoani.mundocore.updating.Updating;

import java.io.IOException;

public class Kosmos extends MundoAddon {

    public Kosmos() {
        super(
                "kosmos",
                ChatColor.DARK_GREEN,
                ChatColor.GREEN,
                ChatColor.AQUA
        );
        link("Metrics", "https://bstats.org/plugin/bukkit/Kosmos");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Documentation.load();
        Updating.load();
        WorldLoader.load();

        Registration.register("Border", BorderMundo::load);
        Registration.register("Chunk", ChunkMundo::load);
        Registration.register("Generator", GeneratorManager::load);
        Registration.register("Miscellaneous", MiscMundo::load);
        Registration.register("WorldCreator", WorldCreatorMundo::load);
        Registration.register("WorldManagement", WorldManagementMundo::load);
    }

    @Override
    public void afterPluginsEnabled() {
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("skript_version", () -> Skript.getVersion().toString()));
    }

    @Override
    public void onDisable() {
        try {
            WorldLoader.save();
            Logging.info("Successfully saved all (if any) world loaders");
        } catch (IOException e) {
            Logging.info("A problem occurred while saving world loaders");
            Logging.reportException(this, e);
        }
    }
}

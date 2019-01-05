package us.tlatoani.kosmos.generator;

import ch.njol.skript.Skript;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Trigger;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import us.tlatoani.mundocore.base.Logging;
import us.tlatoani.mundocore.event_scope.MundoEventScope;
import us.tlatoani.mundocore.event_scope.ScopeUtil;

/**
 * Created by Tlatoani on 8/11/17.
 */
public class ScopeGenerator extends MundoEventScope {
    private GeneratorFunctionality generatorFunctionality;

    @Override
    public void afterInit() {
        generatorFunctionality.load();
        Logging.debug(this, "registered: " + generatorFunctionality);
    }

    @Override
    public void unregister(Trigger trigger) {
        generatorFunctionality.unload();
        Logging.debug(this, "unregistered");
    }

    @Override
    public void unregisterAll() {
        GeneratorManager.unregisterAllSkriptGenerators();
    }

    @Override
    public boolean init(Literal<?>[] literals, int i, SkriptParser.ParseResult parseResult) {
        generatorFunctionality = GeneratorManager.getSkriptGenerator(((Literal<String>) literals[0]).getSingle()).functionality;
        SectionNode topNode = (SectionNode) SkriptLogger.getNode();
        Logging.debug(this, "init()ing");
        try {
            if (generatorFunctionality.isLoaded()) {
                Skript.warning("You may have two 'generator' instances with the id \"" + generatorFunctionality.id + "\" in your code."
                        + " If you do, note that only one of them will be used."
                        + " If you don't, you can ignore this warning.");
                generatorFunctionality.unload();
            }
            nodeLoop: for (Node node : topNode) {
                SkriptLogger.setNode(node);
                Logging.debug(this, "Current node: " + node.getKey());
                if (!(node instanceof SectionNode)) {
                    Skript.error("Code under 'generator' to be run initially should be put under the 'initiation' section!");
                    return false;
                }
                SectionNode subNode = (SectionNode) node;
                for (Subsection subsection : generatorFunctionality) {
                    Kleenean result = subsection.trySectionNode(subNode);
                    if (result == Kleenean.TRUE) {
                        continue nodeLoop;
                    } else if (result == Kleenean.FALSE) {
                        return false;
                    }
                }
                Skript.error("The only sections allowed under 'generator' are 'initiation', 'generation', and 'population'!");
                return false;
            }
            return true;
        } finally {
            ScopeUtil.removeSubNodes(topNode);
        }

    }

    @Override
    public String toString(Event event, boolean b) {
        return "generator \"" + generatorFunctionality.id + "\"";
    }
}
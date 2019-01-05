package us.tlatoani.kosmos.generator;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import us.tlatoani.mundocore.event_scope.ScopeUtil;

import java.util.Optional;

public class Subsection<E extends Event> {
    public final String syntax;
    public final String eventName;
    public final Class<E> eventClass;

    private Optional<TriggerItem> triggerItem = Optional.empty();

    private Optional<SectionNode> sectionNode = Optional.empty();

    public Subsection(String syntax, String eventName, Class<E> eventClass) {
        this.syntax = syntax;
        this.eventName = eventName;
        this.eventClass = eventClass;
    }

    /**
     * Checks if {@code sectionNode} is applicable to this Subsection and returns a Kleenean describing the result.
     * If {@code sectionNode} is applicable and there was no preexisting {@link SectionNode} in place,
     * then {@link Kleenean#TRUE} is returned.
     * If {@code sectionNode} is applicable and there was a preexisting {@link SectionNode} in place,
     * then {@link Skript#error(String)} is called and {@link Kleenean#FALSE} is returned.
     * If {@code sectionNode} is not applicable, then {@link Kleenean#UNKNOWN} is returned.
     * @param sectionNode
     */
    public Kleenean trySectionNode(SectionNode sectionNode) {
        if (sectionNode.getKey().equals(syntax) || sectionNode.getKey().equals("sync " + syntax)) {
            if (this.sectionNode.isPresent()) {
                Skript.error("You cannot have two '" + syntax + "' sections here!");
                return Kleenean.FALSE;
            }
            this.sectionNode = Optional.of(sectionNode);
            return Kleenean.TRUE;
        }
        return Kleenean.UNKNOWN;
    }

    public void load() {
        triggerItem = sectionNode.flatMap(sectionNode -> {
            ScriptLoader.setCurrentEvent(eventName, eventClass);
            return ScopeUtil.loadSectionNode(sectionNode, null);
        });
        sectionNode = Optional.empty();
    }

    public void unload() {
        triggerItem = Optional.empty();
        sectionNode = Optional.empty();
    }

    public void run(E event) {
        triggerItem.ifPresent(t -> TriggerItem.walk(t, event));
    }

    @Override
    public String toString() {
        return "Subsection(syntax: " + syntax + "triggerItem: " + triggerItem + ")";
    }
}

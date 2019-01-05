package us.tlatoani.kosmos.generator;

import com.google.common.collect.Iterators;

import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by Tlatoani on 8/11/17.
 */
public class GeneratorFunctionality implements Iterable<Subsection> {
    public final String id;

    private boolean loaded = false;

    public final Subsection<GeneratorEvent.Initiation> initiation =
            new Subsection<>("initiation", "GeneratorInitiation", GeneratorEvent.Initiation.class);
    public final Subsection<GeneratorEvent.Generation> generation =
            new Subsection<>("generation", "GeneratorGeneration", GeneratorEvent.Generation.class);
    public final Subsection<GeneratorEvent.Population> population =
            new Subsection<>("population", "GeneratorPopulation", GeneratorEvent.Population.class);

    public GeneratorFunctionality() {
        this(null);
    }

    public GeneratorFunctionality(String id) {
        this.id = id;
    }

    public Iterator<Subsection> iterator() {
        return Iterators.forArray(initiation, generation, population);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void load() {
        loaded = true;
        for (Subsection subsection : this) {
            subsection.load();
        }
    }

    public void unload() {
        loaded = false;
        for (Subsection subsection : this) {
            subsection.unload();
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "GeneratorFunctionality(", ")");
        for (Subsection subsection : this) {
            stringJoiner.add(subsection.toString());
        }
        return stringJoiner.toString();
    }
}
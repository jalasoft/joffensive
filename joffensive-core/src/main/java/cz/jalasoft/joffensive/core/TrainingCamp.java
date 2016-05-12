package cz.jalasoft.joffensive.core;

import cz.jalasoft.joffensive.core.battle.BattleBootstrap;
import cz.jalasoft.joffensive.core.platoon.SinglePlatoon;
import cz.jalasoft.joffensive.core.platoon.Skill;

import java.util.function.Supplier;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */
public final class TrainingCamp {

    String name;
    int recruitsCount;
    Cadence cadence;
    int magazineSize;

    private final BattleBootstrap battleBootstrap;

    TrainingCamp(BattleBootstrap battleBootstrap) {
        this.battleBootstrap = battleBootstrap;
    }

    public TrainingCamp ofRecruits(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Number of recruits must be greater than 1.");
        }

        this.recruitsCount = number;
        return this;
    }

    public TrainingCamp shooting(Supplier<Cadence> cadenceSupplier) {
        if (cadenceSupplier == null) {
            throw new IllegalArgumentException("Cadence supplier must not be null or empty.");
        }

        return shooting(cadenceSupplier.get());
    }

    public TrainingCamp shooting(Cadence cadence) {
        if (cadence == null) {
            throw new IllegalArgumentException("Cadence must not be null.");
        }

        this.cadence = cadence;
        return this;
    }

    public TrainingCamp havingMagazinesOfSize(int bulletCount) {
        if (bulletCount < 1) {
            throw new IllegalArgumentException("Size of magazine must be at least 1.");
        }

        this.magazineSize = bulletCount;
        return this;
    }

    public TrainingCamp name(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name of a new platoon must not be null or empty.");
        }

        this.name = name;
        return this;
    }

    public Platoon graduate() {
        checkAllInserted();
        return new SinglePlatoon(name, recruitsCount, new Skill(cadence, magazineSize), battleBootstrap);
    }

    private void checkAllInserted() {
        if (name == null) {
            throw new IllegalStateException("Name has not been inserted.");
        }

        if (cadence == null) {
            throw new IllegalStateException("Cadence has not been inserted.");
        }

        if (recruitsCount == 0) {
            throw new IllegalStateException("Number of recruits has not been inserted.");
        }

        if (magazineSize == 0) {
            throw new IllegalStateException("Magazine size has not been inserted.");
        }
    }
}

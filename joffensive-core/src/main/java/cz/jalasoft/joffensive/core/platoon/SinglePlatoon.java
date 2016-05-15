package cz.jalasoft.joffensive.core.platoon;

import cz.jalasoft.joffensive.core.battle.BattleBootstrap;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-25.
 */
public final class SinglePlatoon extends AbstractPlatoon {

    private final String name;
    private final int size;
    private final Skill skill;

    public SinglePlatoon(String name, int size, Skill skill, BattleBootstrap battleBootstrap) {
        super(battleBootstrap);

        this.name = name;
        this.size = size;
        this.skill = skill;
    }

    @Override
    public Collection<SinglePlatoon> platoons() {
        return Arrays.asList(this);
    }

    public String name() {
        return name;
    }

    public int size() {
        return size;
    }

    public Skill skill() {
        return skill;
    }
}

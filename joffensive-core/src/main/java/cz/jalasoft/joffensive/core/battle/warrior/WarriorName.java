package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.platoon.SinglePlatoon;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-15.
 */
public final class WarriorName {

    private static final String VALUE_PATTERN = "%s[%d]";

    static WarriorName from(SinglePlatoon platoon, int position) {
        return new WarriorName(platoon.name(), position);
    }

    private WarriorName(String platoonName, int position) {
        this.platoonName = platoonName;
        this.position = position;
    }

    private final String platoonName;
    private final int position;

    public String value() {
        return String.format(VALUE_PATTERN, platoonName, position);
    }

    public int position() {
        return position;
    }

    public String platoonName() {
        return platoonName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return false;
        }

        if (!(obj instanceof WarriorName)) {
            return false;
        }

        WarriorName that = (WarriorName) obj;

        if (!this.platoonName().equals(that.platoonName())) {
            return false;
        }

        return this.position() == that.position();
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = result * 37 + platoonName().hashCode();
        result = result * 37 + position();

        return result;
    }

    @Override
    public String toString() {
        return "WarriorName[" + value() + "]";
    }
}

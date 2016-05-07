package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.invokable.Invokable;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-05-02.
 */
public final class WeaponDefinition {

    public static Builder newDefinition() {
        return new Builder();
    }

    //--------------------------------------------------
    //INSTANCE SCOPE
    //--------------------------------------------------

    private final String name;
    private final Class<?> type;

    private final Invokable<Void> prepareInvokable;
    private final Invokable<Void> cleanInvokable;
    private final Invokable<Recoil> shootInvokable;

    private WeaponDefinition(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.prepareInvokable = builder.prepareInvokable;
        this.cleanInvokable = builder.cleanInvokable;
        this.shootInvokable = builder.shootInvokable;
    }

    public String name() {
        return name;
    }

    public Class<?> type() {
        return type;
    }

    public Invokable<Void> prepare() {
        return prepareInvokable;
    }

    public Invokable<Void> clean() {
        return cleanInvokable;
    }

    public Invokable<Recoil> shoot() {
        return shootInvokable;
    }

    //---------------------------------------------------
    //BUILDER
    //---------------------------------------------------

    public static final class Builder {

        private String name;
        private Class<?> type;

        private Invokable<Void> prepareInvokable = Invokable.DUMMY;
        private Invokable<Void> cleanInvokable = Invokable.DUMMY;
        private Invokable<Recoil> shootInvokable;

        public Builder on(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder called(String name) {
            this.name = name;
            return this;
        }

        public Builder preparing(Invokable<Void> invokable) {
            this.prepareInvokable = invokable;
            return this;
        }

        public Builder shooting(Invokable<Recoil> invokable) {
            this.shootInvokable = invokable;
            return this;
        }

        public Builder cleaning(Invokable<Void> invokable) {
            this.cleanInvokable = invokable;
            return this;
        }

        public WeaponDefinition get() {
            assertState();
            return new WeaponDefinition(this);
        }

        private void assertState() {
            if (type == null) {
                throw new IllegalStateException("Weapon type has not been provided.");
            }
            if (name == null) {
                throw new IllegalStateException("Name has not been provided.");
            }
            if (shootInvokable == null) {
                throw new IllegalStateException("Shoot invokable has not been provided.");
            }
        }
    }
}

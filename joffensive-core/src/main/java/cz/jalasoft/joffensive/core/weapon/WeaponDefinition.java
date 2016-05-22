package cz.jalasoft.joffensive.core.weapon;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.weapon.invokable.Invokable;

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

    private final Invokable<Void> beforeWeaponInvokable;
    private final Invokable<Void> beforeShootInvokable;
    private final Invokable<Recoil> shootInvokable;
    private final Invokable<Void> afterShootInvokable;
    private final Invokable<Void> afterWeaponInvokable;

    private WeaponDefinition(Builder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.beforeWeaponInvokable = builder.beforeWeaponInvokable;
        this.beforeShootInvokable = builder.beforeShootInvokable;
        this.shootInvokable = builder.shootInvokable;
        this.afterShootInvokable = builder.afterShootInvokable;
        this.afterWeaponInvokable = builder.afterWeaponInvokable;
    }

    public String name() {
        return name;
    }

    public Class<?> type() {
        return type;
    }

    public Invokable<Void> beforeWeapon() {
        return beforeWeaponInvokable;
    }

    public Invokable<Void> beforeShoot() {
        return beforeShootInvokable;
    }

    public Invokable<Recoil> shoot() {
        return shootInvokable;
    }

    public Invokable<Void> afterShoot() {
        return afterShootInvokable;
    }

    public Invokable<Void> afterWeapon() {
        return afterWeaponInvokable;
    }

    //---------------------------------------------------
    //BUILDER
    //---------------------------------------------------

    public static final class Builder {

        private String name;
        private Class<?> type;

        private Invokable<Void> beforeWeaponInvokable = Invokable.DUMMY;
        private Invokable<Void> beforeShootInvokable = Invokable.DUMMY;
        private Invokable<Recoil> shootInvokable;
        private Invokable<Void> afterShootInvokable = Invokable.DUMMY;
        private Invokable<Void> afterWeaponInvokable = Invokable.DUMMY;

        public Builder on(Class<?> type) {
            this.type = type;
            return this;
        }

        public Builder called(String name) {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Name mut not be null or empty.");
            }
            this.name = name;
            return this;
        }

        public Builder beforeWeapon(Invokable<Void> invokable) {
            if (invokable == null) {
                throw new IllegalArgumentException("Preparing invokable must not be null or empty.");
            }
            this.beforeWeaponInvokable = invokable;
            return this;
        }

        public Builder beforeShoot(Invokable<Void> invokable) {
            if (invokable == null) {
                throw new IllegalArgumentException("Preparing invokable must not be null or empty.");
            }
            this.beforeShootInvokable = invokable;
            return this;
        }

        public Builder shooting(Invokable<Recoil> invokable) {
            if (invokable == null) {
                throw new IllegalArgumentException("Shooting invokable must not be null or empty.");
            }
            this.shootInvokable = invokable;
            return this;
        }

        public Builder afterShoot(Invokable<Void> invokable) {
            if (invokable == null) {
                throw new IllegalArgumentException("Cleaning invokable must not be null or empty.");
            }
            this.afterShootInvokable = invokable;
            return this;
        }

        public Builder afterWeapon(Invokable<Void> invokable) {
            if (invokable == null) {
                throw new IllegalArgumentException("Cleaning invokable must not be null or empty.");
            }
            this.afterWeaponInvokable = invokable;
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

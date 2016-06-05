package cz.jalasoft.joffensive.core.battle.warrior;

import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.Weapon;
import cz.jalasoft.joffensive.core.battle.report.ShootReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
final class ShootingAndReportingWarrior implements Warrior {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShootingAndReportingWarrior.class);

    private final WarriorName name;
    private final Weapon weapon;
    private final Consumer<ShootReport> reporter;

    ShootingAndReportingWarrior(WarriorName name, Weapon weapon, Consumer<ShootReport> reporter) {
        this.name = name;
        this.weapon = weapon;
        this.reporter = reporter;
    }

    @Override
    public WarriorName name() {
        return name;
    }

    @Override
    public void fight() {
        try {
            Recoil recoil = weapon.shoot();

        } catch (TimeoutException exc) {

        } catch (Exception exc) {

        }
    }
}

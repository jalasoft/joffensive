package cz.jalasoft.joffensive;

import cz.jalasoft.joffensice.Battle;
import cz.jalasoft.joffensice.JOffensive;
import cz.jalasoft.joffensice.Platoon;
import cz.jalasoft.joffensice.http.HttpTargetDescription;
import cz.jalasoft.joffensice.weapon.TargetDescription;
import cz.jalasoft.joffensice.weapon.Weapon;
import cz.jalasoft.joffensice.http.HttpWeapon;
import org.testng.annotations.Test;

import static cz.jalasoft.joffensice.JOffensive.regularCadence;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-16.
 */
public class DesignTester {

    @Test
    public void test() {

        JOffensive offensive = JOffensive.load();

        Platoon platoon = offensive.trainingCamp()
                .ofRecruits(3)
                .shooting(regularCadence()
                        .withDelay(3, SECONDS)
                        .every(1, SECONDS)
                        .just(10))
                .reGroup();

        offensive.weapon(HttpWeapon.class)
                .bullets().and()
                .pointingAt().host("sadasd").port(23).path("ssss").and()
                .of(23);


        //.and()
        //.load();

    }
}

package cz.jalasoft.joffensive.test;

import cz.jalasoft.joffensive.core.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public class AnotherDesignTester {

    /*
    @Test
    public void weaponShootsOneBullet() {

        Weapon addWeapon = HttpWeapon.newWeapon().methodGet().target("http://vodnisvetkolin-jalasoft.rhcloud.com:80/status").finalize();

        Recoil result = addWeapon.shoot();

        System.out.println(result);
    }

    @Test
    public void warriorShoots() throws IOException, InterruptedException {

        Weapon addWeapon = HttpWeapon.newWeapon().target("http://vodnisvetkolin-jalasoft.rhcloud.com:80/status").methodGet().finalize();

        ShootingAndReportingWarrior warrior = new ShootingAndReportingWarrior("w1", addWeapon, EvenCadence.shooting().afterSeconds(2).everySecond(4).finalize(), 4);

        Future<Void> fight = CompletableFuture.runAsync(() -> warrior.run());

        TimeUnit.SECONDS.sleep(20);

        fight.cancel(true);

        System.out.println("Konec");
    }
*/
    @Test
    public void scansWeaponAndRegistersItAndRetrievesItAndShoots() throws Exception {

        try(JOffensive g = JOffensive.offensive()
                .shootTimeout(3, TimeUnit.SECONDS)
                .scanAnnotatedClassesAt("cz.jalasoft.joffensive")
                .get()) {

            Weapon weapon = g.weapon("hovnomet");

            Assert.assertNotNull(weapon);

            Platoon platoon = g
                    .trainingCamp()
                    .ofRecruits(4)
                    .called("Bazanti")
                    .shooting(EvenCadence.evenly().everySecond(4))
                    .havingMagazinesOfSize(2)
                    .graduate();

            TimeUnit.SECONDS.sleep(3);

            Battle battle = platoon.fire(weapon);

            TimeUnit.SECONDS.sleep(10);

            battle.ceaseFire();
        }


        /*
        JOffensive o = new JOffensive();



        Platoon platoon = o.trainingCamp()
                .ofRecruits(5)
                .cadence(EvenCadence.shooting().everySecond(3).finalize())
                .withBullets(20)
                .reGroup();

        Shoot addWeapon = o.addWeapon("http");

        platoon.fire(addWeapon);*/
    }
}

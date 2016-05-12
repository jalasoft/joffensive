package cz.jalasoft.joffensive.test;

import cz.jalasoft.joffensive.core.*;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public class AnotherDesignTester {

    /*
    @Test
    public void weaponShootsOneBullet() {

        Weapon weapon = HttpWeapon.newWeapon().methodGet().target("http://vodnisvetkolin-jalasoft.rhcloud.com:80/status").get();

        Recoil result = weapon.shoot();

        System.out.println(result);
    }

    @Test
    public void warriorShoots() throws IOException, InterruptedException {

        Weapon weapon = HttpWeapon.newWeapon().target("http://vodnisvetkolin-jalasoft.rhcloud.com:80/status").methodGet().get();

        Warrior warrior = new Warrior("w1", weapon, EvenCadence.shooting().afterSeconds(2).everySecond(4).get(), 4);

        Future<Void> fight = CompletableFuture.runAsync(() -> warrior.run());

        TimeUnit.SECONDS.sleep(20);

        fight.cancel(true);

        System.out.println("Konec");
    }
*/
    @Test
    public void scansWeaponAndRegistersItAndRetrievesItAndShoots() throws Exception {

        JOffensive g = JOffensive.newOffensive();
        g.registerWeaponsInPackage("cz.jalasoft.joffensive");

        Weapon weapon = g.weapon("hovnomet");

        Assert.assertNotNull(weapon);

        Platoon platoon = g
                .trainingCamp()
                .ofRecruits(4)
                .name("Bazanti")
                .shooting(EvenCadence.evenly().everySecond(4))
                .havingMagazinesOfSize(2)
                .graduate();

        platoon.regroup()

        weapon.shoot();

        /*
        JOffensive o = new JOffensive();



        Platoon platoon = o.trainingCamp()
                .ofRecruits(5)
                .cadence(EvenCadence.shooting().everySecond(3).get())
                .withBullets(20)
                .reGroup();

        Shoot weapon = o.weapon("http");

        platoon.fire(weapon);*/
    }
}

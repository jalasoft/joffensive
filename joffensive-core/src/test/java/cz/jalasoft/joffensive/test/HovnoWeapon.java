package cz.jalasoft.joffensive.test;


import cz.jalasoft.joffensive.core.weapon.annotation.CleanWeapon;
import cz.jalasoft.joffensive.core.weapon.annotation.PrepareWeapon;
import cz.jalasoft.joffensive.core.weapon.annotation.Shoot;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */

public class HovnoWeapon {

    @PrepareWeapon
    public void nabij() {
        System.out.println("Nabijim zbran");
    }

    @Shoot("hovnomet")
    public void g() {
        System.out.println("Striliiiiim");
    }

    @CleanWeapon
    public void vycisti() {
        System.out.println("Cistim zbran");
    }
}

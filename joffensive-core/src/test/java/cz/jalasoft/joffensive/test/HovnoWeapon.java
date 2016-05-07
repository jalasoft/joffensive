package cz.jalasoft.joffensive.test;


import cz.jalasoft.joffensive.core.Recoil;
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
    public Recoil g() {
        System.out.println("Striliiiiim");
        return null;
    }

    @CleanWeapon
    public void vycisti() {
        System.out.println("Cistim zbran");
    }
}

package cz.jalasoft.joffensive.test;


import cz.jalasoft.joffensive.core.Recoil;
import cz.jalasoft.joffensive.core.weapon.annotation.*;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-23.
 */

public class HovnoWeapon {

    @BeforeWeapon
    public void vyndejZbran() {
        System.out.println("Vyndavam zbran");
    }

    @BeforeShooting
    public void nabij() {
        System.out.println("Nabijim zbran");
    }

    @Shoot("hovnomet")
    public Recoil g() {
        System.out.println("Striliiiiim");
        return null;
    }

    @AfterShooting
    public void vycisti() {
        System.out.println("Cistim zbran");
    }

    @AfterWeapon
    public void zandejZbran() {
        System.out.println("Zandavam zbran");
    }
}

package cz.jalasoft.joffensice;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-12.
 */
public interface Platoon {

    Platoon form(Platoon another);

    Battle attack(Enemy enemy);
}

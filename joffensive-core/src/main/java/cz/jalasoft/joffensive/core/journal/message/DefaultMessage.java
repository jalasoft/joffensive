package cz.jalasoft.joffensive.core.journal.message;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-06-09.
 */
abstract class DefaultMessage implements Message {

    private String name;
    private long timestamp;

    DefaultMessage(String name) {
        this.name = name;
        this.timestamp =
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final long timetamp() {
        return timestamp;
    }
}

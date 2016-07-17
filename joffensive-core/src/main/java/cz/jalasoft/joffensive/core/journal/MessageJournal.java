package cz.jalasoft.joffensive.core.journal;

import cz.jalasoft.joffensive.core.journal.message.Message;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-06-09.
 */
public interface MessageJournal {

    void accept(Message message);
}

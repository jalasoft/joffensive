package cz.jalasoft.joffensice;

import cz.jalasoft.joffensice.http.HttpMissionBriefing;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-15.
 */
public class MissionBriefing {
    public HttpMissionBriefing http() {
        return new HttpMissionBriefing();
    }
}

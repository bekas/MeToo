/**
 * 
 */
package com.metoo.gmap.overlay;

import com.metoo.gmap.MapProvider;
import com.metoo.model.Event;

/**
 * Элемент-оверлей "Встреча"
 * @author Theurgist
 */
public class MeetingMapItem extends BaseMapItem {
	/**
	 * Ассоциированная встреча
	 */
	private Event meeting;

	/**
	 * @param meeting Связанный экземпляр сущности "Встреча"
	 */
	public MeetingMapItem(Event meeting) {
		super(	MapProvider.ConvertCoordinates(meeting.Latitude, meeting.Longitude),
				meeting.Name, meeting.Information);
		this.meeting = meeting;
	}

}

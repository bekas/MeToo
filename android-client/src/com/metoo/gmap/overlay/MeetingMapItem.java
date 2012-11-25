/**
 * 
 */
package com.metoo.gmap.overlay;

import com.metoo.gmap.MapProvider;
import com.metoo.model.Meeting;

/**
 * Элемент-оверлей "Встреча"
 * @author Theurgist
 */
public class MeetingMapItem extends BaseMapItem {
	/**
	 * Ассоциированная встреча
	 */
	private Meeting meeting;

	/**
	 * @param meeting Связанный экземпляр сущности "Встреча"
	 */
	public MeetingMapItem(Meeting meeting) {
		super(	MapProvider.ConvertCoordinates(meeting.Latitude, meeting.Longitude),
				meeting.Name, meeting.Information);
		this.meeting = meeting;
	}

}

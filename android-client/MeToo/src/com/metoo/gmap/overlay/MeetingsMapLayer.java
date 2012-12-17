package com.metoo.gmap.overlay;

import java.util.List;

import com.google.android.maps.MapView;
import com.metoo.common.androidutils.AndroServices;
import com.metoo.model.Event;
import com.metoo.model.EventList;

import android.graphics.drawable.Drawable;

/**
 * Слой встреч - отображение на карте событий
 * @author Theurgist
 */
public class MeetingsMapLayer extends MapItemsLayer {
	
	AndroServices services;
	MapView mapView;
	
	/**
	 * Обратный вызов при нажатии пользователем какого-то оверлея
	 */
	IOnItemTap onItemTapped;
	
	/**
	 * Список событий для отображения на карте
	 */
	protected EventList evlist;

	public MeetingsMapLayer(Drawable defaultMarker, AndroServices services, MapView mapView) {
		super(defaultMarker);
		this.services = services;
		this.mapView = mapView;
		evlist = new EventList();
	}
	
	/**
	 * Слияние списка событий в список уже загруженных. При добавлении
	 * новых событий карта обновляется.
	 * @param incoming Список запрошенных с сервера событий
	 */
	public void MergeNewEvents(EventList incoming) {
		if ((incoming == null) || (incoming.Length() < 1))
			return;
			
		List<Event> added = evlist.Merge(incoming);
		for(Event ev: added) {
			addOverlay(new MeetingMapItem(ev));
		}
		mapView.postInvalidate();
	}
	
	/**
	 * Обработка нажатия по оверлейному элементу
	 */
	 @Override
	 protected boolean onTap(int index) {
		BaseMapItem item = mOverlays.get(index);
		
		if (onItemTapped != null)
			onItemTapped.onItemTap(item);
		else
			services.ShowInfoAlert(item.getTitle(), item.getSnippet());
		
		return true;
		 
	 }
	 
	 /**
	  * Регистрация слушателя события тапа по оверлею
	  * @param listener
	  */
	 public void SetOnItemTappedListener(IOnItemTap listener) {
		 onItemTapped = listener;
	 }
	 
	 /**
	  * Слушатель события тапа по оверлею
	  * @author theurgist
	  */
	 public interface IOnItemTap {
		 void onItemTap(BaseMapItem tapped);
	 }
	 
}

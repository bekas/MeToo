package com.metoo.activities;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.metoo.R;
import com.metoo.common.AppSettings;
import com.metoo.common.MetooServices;
import com.metoo.common.androidutils.AndroServices;
import com.metoo.common.androidutils.AndroidAppLog;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;
import com.metoo.gmap.overlay.MeetingMapItem;
import com.metoo.gmap.overlay.MeetingsMapLayer;
import com.metoo.model.Event;
import com.metoo.srvlink.answers.EventListAnswer;
import com.metoo.srvlink.requests.GetEventsRequest;
import com.metoo.ui.MapLayout;
import com.metoo.ui.views.IMapViewPanListener;
import com.metoo.ui.views.IOnLongPressListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class NavActivity extends MapActivity 
{
	private MapLayout layout;
	private MeetingsMapLayer meetingsCache;
	private AndroServices services;


	// Параметры экрана
//	private float ds;
//	private int width, height;
	GeoPoint cachedBottomLeft, cachedTopRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidAppLog.I("NavActivity started");
        services = new AndroServices(this);
        layout = new MapLayout(this);
        layout.Activate(new MapViewHook());
        
        initMeetingsOverlay();

		if (AppSettings.GetEmulationMode())
			emulatedSituation();
		
//		ds = getApplicationContext().getResources().getDisplayMetrics().density;
//	    width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
//	    height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
		
		int[][] bounds = layout.mapView.getBounds();
		cachedBottomLeft = new GeoPoint(bounds[0][0], bounds[0][1]);
		cachedTopRight = new GeoPoint(bounds[1][0], bounds[1][1]);

    }
    /**
     * Инициализация слоя встреч на карте (с созданием значка встречи по умолчанию)
     */
    private void initMeetingsOverlay() {
		Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.token_orange);
    	meetingsCache = new MeetingsMapLayer(drawable, services, layout.mapView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return layout.onCreateOptionsMenu(menu);
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	layout.onKeyDown(keyCode, event);
    	return super.onKeyDown(keyCode, event);
    }  
	
	

	/**
	 * Добавить несколько фейковых встреч в целях тестирования
	 */
	private void emulatedSituation() {
		if (true)
			return;
		
		com.metoo.model.Event meeting1 = new com.metoo.model.Event();
		meeting1.Id = 999991;
		meeting1.Name = "Тусовка на севере";
		meeting1.Description = "Дискотека на открытом воздухе";
		meeting1.Latitude = 55.9;
		meeting1.Longitude = 37.8;
		MeetingMapItem overlayitem = new MeetingMapItem(meeting1);

		Event meeting2 = new Event();
		meeting2.Id = 999992;
		meeting2.Name = "Тусовка на юге";
		meeting2.Description = "Будет весело!";
		meeting2.Latitude = 55.4;
		meeting2.Longitude = 37.4;
		MeetingMapItem overlayitem2 = new MeetingMapItem(meeting2);

		Event meeting3 = new Event();
		meeting3.Id = 999993;
		meeting3.Name = "Имя события";
		meeting3.Description = "Тут что-то будет";
		meeting3.Latitude = 55.6;
		meeting3.Longitude = 37.45;
		MeetingMapItem overlayitem3 = new MeetingMapItem(meeting3);

		Event meeting4 = new Event();
		meeting4.Id = 999994;
		meeting4.Name = "Тут что-то было";
		meeting4.Description = "Всё закончилось";
		meeting4.Latitude = 55.2;
		meeting4.Longitude = 37.55;
		MeetingMapItem overlayitem4 = new MeetingMapItem(meeting4);

		meetingsCache.addOverlay(overlayitem);
		meetingsCache.addOverlay(overlayitem2);
		meetingsCache.addOverlay(overlayitem3);
		meetingsCache.addOverlay(overlayitem4);
		
		layout.AddLayer(meetingsCache);
	}
	
	
	/**
	 * Перехватчик панорамирования и зуммирования карты. При возникновении этих событий 
	 * вычисляется новый координатный охват отображаемой карты и отправляется запрос
	 * на сервер для получения событий в этом охвате
	 * @author theurgist
	 */
	class MapViewHook implements IMapViewPanListener {

		public void onPan(GeoPoint oldTopLeft, GeoPoint oldCenter,
				GeoPoint oldBottomRight, GeoPoint newTopLeft,
				GeoPoint newCenter, GeoPoint newBottomRight) {
			updateFromServer();
		}

		public void onZoom(GeoPoint oldTopLeft, GeoPoint oldCenter,
				GeoPoint oldBottomRight, GeoPoint newTopLeft,
				GeoPoint newCenter, GeoPoint newBottomRight, int oldZoomLevel,
				int newZoomLevel) {
			if (newZoomLevel > newZoomLevel)
				updateFromServer();
		}

		public void onClick(GeoPoint clickedPoint) {
			// TODO Auto-generated method stub
			
		}
		
		private void updateFromServer() {
			GeoPoint center = layout.mapView.getMapCenter();
//			Location l0 = new Location("none");
//			Location l1 = new Location("none");
//			
//	        //Calculate scale bar size and units
//	        GeoPoint g0 = layout.mapView.getProjection().fromPixels(0, height/2);
//	        GeoPoint g1 = layout.mapView.getProjection().fromPixels(width, height/2);
//	        l0.setLatitude(g0.getLatitudeE6()/1E6);
//	        l0.setLongitude(g0.getLongitudeE6()/1E6);
//	        l1.setLatitude(g1.getLatitudeE6()/1E6);
//	        l1.setLongitude(g1.getLongitudeE6()/1E6);
//	        float distance = l0.distanceTo(l1);
			
	        
	        int maxSpan = Math.min( layout.mapView.getLatitudeSpan(), 
	        						layout.mapView.getLongitudeSpan());
	        
			GetEventsRequest req = new GetEventsRequest(
					(double)center.getLatitudeE6() / 1E6, 
					(double)center.getLongitudeE6() / 1E6, 
					maxSpan/1E6*5);
			try {
				MetooServices.Request(req, EventListAnswer.class, new MapDataReceiver());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * Внутренний объект-получатель ответа с сервера со списком событий в
	 * запрошенном радиусе
	 * @author theurgist
	 */
	class MapDataReceiver implements IAsyncTaskNotifyer<EventListAnswer, String, String> {

		public void onSuccess(EventListAnswer Result) {

//			PageParser parser = new PageParser();
//			EventListAnswer answ = new EventListAnswer(Result, parser);
			
			if (Result.GetError() != null) {
				services.ShowInfoAlert("Ошибка", Result.GetError());
			} else {
				meetingsCache.MergeNewEvents(Result.GetEvents());
			}
		}
		public void onError(String Reason) {
			services.ShowToast("Ошибка в модуле MapDataReceiver: " + Reason);
		}
		public void onProgress(String Message) {
		}
	}

	/**
	 * Перехватчик
	 * @author theurgist
	 *
	 */
	class LongTapOnMap implements IOnLongPressListener {

		@Override
		public void onLongpress(GeoPoint longpressLocation) {

        	Intent myIntent = new Intent(NavActivity.this, CreateEventActivity.class);
       	 	startActivity(myIntent);
        	AndroidAppLog.E("Launching activity for event creating");
		}
		
	}
}

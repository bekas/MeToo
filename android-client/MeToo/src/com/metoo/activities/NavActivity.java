package com.metoo.activities;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.metoo.R;
import com.metoo.common.AndroServices;
import com.metoo.common.AppLog;
import com.metoo.common.AppSettings;
import com.metoo.common.IAsyncTaskNotifyer;
import com.metoo.gmap.overlay.MapItemsLayer;
import com.metoo.gmap.overlay.MeetingMapItem;
import com.metoo.gmap.overlay.MeetingsMapLayer;
import com.metoo.model.Event;
import com.metoo.srvlink.Connector;
import com.metoo.srvlink.XmlAnswer;
import com.metoo.srvlink.messages.GetEvents;
import com.metoo.ui.MapLayout;
import com.metoo.ui.views.MapViewListener;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class NavActivity extends MapActivity 
{
	private MapLayout layout;
	private MeetingsMapLayer meetingsCache;
	private AndroServices services;
	
	Connector connect;

	// Параметры экрана
	private float ds;
	private int width, height;
	GeoPoint cachedBottomLeft, cachedTopRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.I("NavActivity started");
        services = new AndroServices(this);
        layout = new MapLayout(this);
        layout.Activate(new MapViewHook());
        
        initMeetingsOverlay();

		if (AppSettings.GetEmulationMode())
			emulatedSituation();
		
		ds = getApplicationContext().getResources().getDisplayMetrics().density;
	    width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
	    height = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
		
		int[][] bounds = layout.mapView.getBounds();
		cachedBottomLeft = new GeoPoint(bounds[0][0], bounds[0][1]);
		cachedTopRight = new GeoPoint(bounds[1][0], bounds[1][1]);

		connect = new Connector("http", AppSettings.GetSrvUrl()+":"+AppSettings.GetSrvPort());
    }
    private void initMeetingsOverlay() {
		Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.token_orange);
    	meetingsCache = new MeetingsMapLayer(drawable, services);
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
	
	

	private void emulatedSituation() {
		com.metoo.model.Event meeting = new com.metoo.model.Event();
		meeting.Name = "Тусовка на севере";
		meeting.Information = "Дискотека на открытом воздухе";
		meeting.Latitude = 55.9;
		meeting.Longitude = 37.8;
		MeetingMapItem overlayitem = new MeetingMapItem(meeting);

		Event meeting2 = new Event();
		meeting2.Name = "Тусовка на юге";
		meeting2.Information = "Будет весело!";
		meeting2.Latitude = 55.4;
		meeting2.Longitude = 37.4;
		MeetingMapItem overlayitem2 = new MeetingMapItem(meeting2);

		Event meeting3 = new Event();
		meeting2.Name = "Имя события";
		meeting2.Information = "Тут что-то будет";
		meeting2.Latitude = 55.6;
		meeting2.Longitude = 37.45;
		MeetingMapItem overlayitem3 = new MeetingMapItem(meeting2);

		Event meeting4 = new Event();
		meeting2.Name = "Тут что-то было";
		meeting2.Information = "Всё закончилось";
		meeting2.Latitude = 55.2;
		meeting2.Longitude = 37.55;
		MeetingMapItem overlayitem4 = new MeetingMapItem(meeting2);

		meetingsCache.addOverlay(overlayitem);
		meetingsCache.addOverlay(overlayitem2);
		meetingsCache.addOverlay(overlayitem3);
		meetingsCache.addOverlay(overlayitem4);
		
		layout.AddLayer(meetingsCache);
	}
	
	
	class MapViewHook implements MapViewListener {

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
			Location l0 = new Location("none");
			Location l1 = new Location("none");
			
	        //Calculate scale bar size and units
	        GeoPoint g0 = layout.mapView.getProjection().fromPixels(0, height/2);
	        GeoPoint g1 = layout.mapView.getProjection().fromPixels(width, height/2);
	        l0.setLatitude(g0.getLatitudeE6()/1E6);
	        l0.setLongitude(g0.getLongitudeE6()/1E6);
	        l1.setLatitude(g1.getLatitudeE6()/1E6);
	        l1.setLongitude(g1.getLongitudeE6()/1E6);
	        float distance = l0.distanceTo(l1);
			
	        
//	        int maxSpan = Math.min( layout.mapView.getLatitudeSpan(), 
//	        						layout.mapView.getLongitudeSpan());
	        
			GetEvents req = new GetEvents(
					(double)center.getLatitudeE6() / 1E6, 
					(double)center.getLongitudeE6() / 1E6, 
					distance*5);
			try {
				connect.SendSimpleRequest(req, new MapDataReceiver());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class MapDataReceiver implements IAsyncTaskNotifyer<String, String, String> {

		public void onSuccess(String Result) {
			XmlAnswer ans = new XmlAnswer();
			ans.ParseMessage(Result);
			
			
		}
		public void onError(String Reason) {
		}
		public void onProgress(String Message) {
		}
	}
}

package com.metoo.activities;

import com.google.android.maps.MapActivity;
import com.metoo.R;
import com.metoo.common.AndroServices;
import com.metoo.common.AppLog;
import com.metoo.common.AppSettings;
import com.metoo.gmap.overlay.MapItemsLayer;
import com.metoo.gmap.overlay.MeetingMapItem;
import com.metoo.gmap.overlay.MeetingsMapLayer;
import com.metoo.model.Meeting;
import com.metoo.ui.MapLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class NavActivity extends MapActivity 
{
	private MapLayout layout;
	private MeetingsMapLayer meetingsCache;
	private AndroServices services;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.I("NavActivity started");
        services = new AndroServices(this);
        layout = new MapLayout(this);
        layout.Activate();
        
        initMeetingsOverlay();

		if (AppSettings.GetEmulationMode())
			emulatedSituation();
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
		Meeting meeting = new Meeting();
		meeting.Name = "Тусовка на севере";
		meeting.Information = "Дискотека на открытом воздухе";
		meeting.Latitude = 55.9;
		meeting.Longitude = 37.8;
		MeetingMapItem overlayitem = new MeetingMapItem(meeting);

		Meeting meeting2 = new Meeting();
		meeting2.Name = "Тусовка на юге";
		meeting2.Information = "Будет весело!";
		meeting2.Latitude = 55.4;
		meeting2.Longitude = 37.4;
		MeetingMapItem overlayitem2 = new MeetingMapItem(meeting2);
		
		meetingsCache.addOverlay(overlayitem);
		meetingsCache.addOverlay(overlayitem2);
		
		layout.AddLayer(meetingsCache);
	}
}

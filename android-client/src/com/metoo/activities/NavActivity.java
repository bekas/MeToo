package com.metoo.activities;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.metoo.R;
import com.metoo.common.AppLog;
import com.metoo.common.AppSettings;
import com.metoo.gmap.MapProvider;
import com.metoo.gmap.overlay.MapItemsLayer;
import com.metoo.gmap.overlay.MeetingMapItem;
import com.metoo.gmap.overlay.MeetingsMapLayer;
import com.metoo.ui.MapLayout;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class NavActivity extends MapActivity 
{
	private MapLayout layout;
	private MapItemsLayer meetingsCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppLog.I("NavActivity started");
        layout = new MapLayout(this);
        layout.Activate();
        
        initMeetingsOverlay();

		if (AppSettings.GetEmulationMode())
			emulatedSituation();
    }
    private void initMeetingsOverlay() {
		Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.token_orange);
    	meetingsCache = new MeetingsMapLayer(drawable);
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
		GeoPoint point = MapProvider.ConvertCoordinates(55.9, 37.8);
		MeetingMapItem overlayitem = new MeetingMapItem(point, "Где-то на севере", "Рядом Мытищи");
		
		GeoPoint point2 = MapProvider.ConvertCoordinates(55.1, 37.3);
		MeetingMapItem overlayitem2 = new MeetingMapItem(point2, "Где-то на юге", "Неподалёку Чехов");
		
		meetingsCache.addOverlay(overlayitem);
		meetingsCache.addOverlay(overlayitem2);
		
		layout.AddLayer(meetingsCache);
	}
}

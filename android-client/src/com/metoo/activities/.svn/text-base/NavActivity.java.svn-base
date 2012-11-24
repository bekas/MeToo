package com.metoo.activities;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.metoo.R;
import com.metoo.common.AppLog;
import com.metoo.gmap.MapProvider;
import android.os.Bundle;
import android.content.Context;
import android.view.KeyEvent;
import android.view.Menu;

public class NavActivity extends MapActivity 
{
    MapView mapView;
    private static Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ctx = getApplicationContext();
 
        AppLog.I("NavActivity started");
        
        @SuppressWarnings("unused")
		MapProvider mapProv = new MapProvider(this, ctx);
        

//      List<Overlay> mapOverlays = mapView.getOverlays();
//      Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
      
//      MeetingsOverlay itemizedoverlay = new MeetingsOverlay(drawable,this);
//      GeoPoint point = new GeoPoint(30443769,-91158458);
//      OverlayItem overlayitem = new OverlayItem(point, "Laissez les bon temps rouler!", "I'm in Louisiana!");

//      GeoPoint point2 = new GeoPoint(17385812,78480667);
//      OverlayItem overlayitem2 = new OverlayItem(point2, "Namashkaar!", "I'm in Hyderabad, India!");

//      itemizedoverlay.addOverlay(overlayitem);
//      itemizedoverlay.addOverlay(overlayitem2);

//      mapOverlays.add(itemizedoverlay);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

    
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        MapController mc = mapView != null ? mapView.getController() : null; 
        switch (keyCode) 
        {
            case KeyEvent.KEYCODE_3:
            	if (mc != null)
            		mc.zoomIn();
                break;
            case KeyEvent.KEYCODE_1:
            	if (mc != null)
            		mc.zoomOut();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }  
}

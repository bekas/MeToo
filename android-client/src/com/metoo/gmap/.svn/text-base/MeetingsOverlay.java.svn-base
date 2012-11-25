package com.metoo.gmap;

import java.util.ArrayList;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;

public class MeetingsOverlay extends ItemizedOverlay<OverlayItem>
{
	 private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	 private MapActivity activity;
	 private MapView mapView;

	 public MeetingsOverlay(Drawable defaultMarker, MapView mapView, MapActivity activity) {
		 super(boundCenterBottom(defaultMarker));
		 this.activity = activity;
		 this.mapView = mapView;
	 }

	 public void addOverlay(OverlayItem overlay) {
		 mOverlays.add(overlay);
		 populate();
	 }
	 	 
	 @Override
	 protected OverlayItem createItem(int i) {
		 return mOverlays.get(i);
		 }
		 @Override
		 public int size()
		 {
			 return mOverlays.size();
		 }
		 
	 @Override
	 protected boolean onTap(int index) {
		 OverlayItem item = mOverlays.get(index);
		 AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		 dialog.setTitle(item.getTitle());
		 dialog.setMessage(item.getSnippet());
		 dialog.show();
		 return true;
	 }
}

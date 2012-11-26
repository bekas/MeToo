/**
 * 
 */
package com.metoo.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * @author Theurgist
 *
 */
public class MapViewEx extends MapView {

    private GeoPoint mOldTopLeft;
    private GeoPoint mOldCenter;
    private GeoPoint mOldBottomRight;
    private int mOldZoomLevel = -1;
    
	/**
	 * @param context
	 * @param apiKey
	 */
	public MapViewEx(Context context, String apiKey) {
		super(context, apiKey);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MapViewEx(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public MapViewEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
     
    private MapViewListener mMapViewListener;
    public MapViewListener getMapViewListener() { return mMapViewListener; }
    public void setMapViewListener(MapViewListener value) { mMapViewListener = value; }
 
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            GeoPoint newCenter = this.getMapCenter();
            GeoPoint newTopLeft = this.getProjection().fromPixels(0, 0);
            GeoPoint newBottomRight = this.getProjection().fromPixels(this.getWidth(), this.getHeight());
             
            if (this.mMapViewListener != null &&
                newTopLeft.getLatitudeE6() == mOldTopLeft.getLatitudeE6() &&
                newTopLeft.getLongitudeE6() == mOldTopLeft.getLongitudeE6()) {
                mMapViewListener.onClick(this.getProjection().fromPixels((int)ev.getX(), (int)ev.getY()));
            }
        }
        return super.onTouchEvent(ev);
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
         
        GeoPoint newCenter = this.getMapCenter();
        GeoPoint newTopLeft = this.getProjection().fromPixels(0, 0);
        GeoPoint newBottomRight = this.getProjection().fromPixels(this.getWidth(), this.getHeight());
        int newZoomLevel = this.getZoomLevel();
         
        if (mOldCenter == null)
            mOldCenter = newCenter;
         
        if (mOldTopLeft == null)
            mOldTopLeft = newTopLeft;
     
        if (mOldBottomRight == null)
            mOldBottomRight = newBottomRight;
     
        if (newTopLeft.getLatitudeE6() != mOldTopLeft.getLatitudeE6() || newTopLeft.getLongitudeE6() != mOldTopLeft.getLongitudeE6()) {
            if (this.mMapViewListener != null) {
                GeoPoint oldTopLeft, oldCenter, oldBottomRight;
                 
                oldTopLeft = mOldTopLeft;
                oldCenter = mOldCenter;
                oldBottomRight = mOldBottomRight;
         
                mOldBottomRight = newBottomRight;
                mOldTopLeft = newTopLeft;
                mOldCenter = newCenter;
                 
                mMapViewListener.onPan(oldTopLeft,
                                       oldCenter,
                                       oldBottomRight,
                                       newTopLeft,
                                       newCenter,
                                       newBottomRight);
            }
        }
         
        if (mOldZoomLevel == -1)
            mOldZoomLevel = newZoomLevel;
        else if (mOldZoomLevel != newZoomLevel && mMapViewListener != null) {
            int oldZoomLevel = mOldZoomLevel;
            GeoPoint oldTopLeft, oldCenter, oldBottomRight;
            oldTopLeft = mOldTopLeft;
            oldCenter = mOldCenter;
            oldBottomRight = mOldBottomRight;
     
            mOldZoomLevel = newZoomLevel;
            mOldBottomRight = newBottomRight;
            mOldTopLeft = newTopLeft;
            mOldCenter = newCenter;
             
            mMapViewListener.onZoom(oldTopLeft,
                                    oldCenter,
                                    oldBottomRight,
                                    newTopLeft,
                                    newCenter,
                                    newBottomRight,
                                    oldZoomLevel,
                                    newZoomLevel);
        }
    }
    
    public int[][] getBounds() {

        GeoPoint center = getMapCenter();
        int latitudeSpan = getLatitudeSpan();
        int longtitudeSpan = getLongitudeSpan();
        int[][] bounds = new int[2][2];

        bounds[0][0] = center.getLatitudeE6() - (latitudeSpan / 2);
        bounds[0][1] = center.getLongitudeE6() - (longtitudeSpan / 2);

        bounds[1][0] = center.getLatitudeE6() + (latitudeSpan / 2);
        bounds[1][1] = center.getLongitudeE6() + (longtitudeSpan / 2);
        return bounds;
    }



}

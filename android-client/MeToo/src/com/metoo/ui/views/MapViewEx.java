/**
 * 
 */
package com.metoo.ui.views;

import java.util.Timer;
import java.util.TimerTask;

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

    static final int LONGPRESS_THRESHOLD = 5000;
    private Timer longpressTimer = new Timer();
    /**
     * Для проверки, перемещалась ли за время долгого нажатия карта
     */
    private GeoPoint longPressMapCenter;
    
    private IMapViewPanListener mapviewpanListener;
    private IOnLongPressListener longpressListener;
    
    
    
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
     
	
    public IMapViewPanListener getMapViewPanListener() { return mapviewpanListener; }
    public void setMapViewPanListener(IMapViewPanListener listener) {
    	mapviewpanListener = listener;
    }
    public IOnLongPressListener getLongPressListener() {return longpressListener; }
    public void setLongPressListener(IOnLongPressListener listener) {
    	longpressListener = listener;
    }
 
    /**
     * Событие касания
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
    	processLongPress(ev);
    	
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            GeoPoint newCenter = this.getMapCenter();
            GeoPoint newTopLeft = this.getProjection().fromPixels(0, 0);
            GeoPoint newBottomRight = this.getProjection().fromPixels(this.getWidth(), this.getHeight());
             
            if (this.mapviewpanListener != null &&
                newTopLeft.getLatitudeE6() == mOldTopLeft.getLatitudeE6() &&
                newTopLeft.getLongitudeE6() == mOldTopLeft.getLongitudeE6()) {
                mapviewpanListener.onClick(this.getProjection().fromPixels((int)ev.getX(), (int)ev.getY()));
            }
        }
        return super.onTouchEvent(ev);
    }
    
    /**
     * Перехват события отрисовки
     */
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
            if (this.mapviewpanListener != null) {
                GeoPoint oldTopLeft, oldCenter, oldBottomRight;
                 
                oldTopLeft = mOldTopLeft;
                oldCenter = mOldCenter;
                oldBottomRight = mOldBottomRight;
         
                mOldBottomRight = newBottomRight;
                mOldTopLeft = newTopLeft;
                mOldCenter = newCenter;
                 
                mapviewpanListener.onPan(oldTopLeft,
                                       oldCenter,
                                       oldBottomRight,
                                       newTopLeft,
                                       newCenter,
                                       newBottomRight);
            }
        }
         
        if (mOldZoomLevel == -1)
            mOldZoomLevel = newZoomLevel;
        else if (mOldZoomLevel != newZoomLevel && mapviewpanListener != null) {
            int oldZoomLevel = mOldZoomLevel;
            GeoPoint oldTopLeft, oldCenter, oldBottomRight;
            oldTopLeft = mOldTopLeft;
            oldCenter = mOldCenter;
            oldBottomRight = mOldBottomRight;
     
            mOldZoomLevel = newZoomLevel;
            mOldBottomRight = newBottomRight;
            mOldTopLeft = newTopLeft;
            mOldCenter = newCenter;
             
            mapviewpanListener.onZoom(oldTopLeft,
                                    oldCenter,
                                    oldBottomRight,
                                    newTopLeft,
                                    newCenter,
                                    newBottomRight,
                                    oldZoomLevel,
                                    newZoomLevel);
        }
    }
    
    /**
     * Получение границ экрана в географических координатах
     * @return Двумерный массив 2х2: [0][0] - слева внизу, [1][1] - сверху справа
     */
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

    /**
     * Логика для распознавания длительного нажатия
     * @param event - событие, полученное в onTouchEvent()
     */
    private void processLongPress(final MotionEvent event) { 
        if (event.getPointerCount() > 1) {
            // Дополнительное касание - мультитач-событие
            longpressTimer.cancel();
        }
        
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Палец дотронулся
        	
        	if (longpressTimer != null) {
        		longpressTimer.cancel();
        		longpressTimer = null;
        	}
        	
            longpressTimer = new Timer();
            longpressTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    GeoPoint longpressLocation = getProjection().fromPixels((int)event.getX(), 
                            (int)event.getY());
                    // Вызываем подписчика
                    longpressListener.onLongpress(longpressLocation);
                }
            }, LONGPRESS_THRESHOLD);
             
            longPressMapCenter = getMapCenter();
        }
         
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // Палец сдвинул карту
            if (!getMapCenter().equals(longPressMapCenter)) {
                // Отменяем, если центр-таки сменился
                longpressTimer.cancel();
            }
             
            longPressMapCenter = getMapCenter();
        }
         
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            // Палец сняли с карты
            longpressTimer.cancel();
        }
    }


}

/**
 * 
 */
package com.metoo.ui.views;

import com.google.android.maps.GeoPoint;

/**
 * Интерфейс для отслеживания панорамирования карты
 * @author Theurgist
 */
public interface IMapViewPanListener {
    void onPan(GeoPoint oldTopLeft,
               GeoPoint oldCenter,
               GeoPoint oldBottomRight,
               GeoPoint newTopLeft,
               GeoPoint newCenter,
               GeoPoint newBottomRight);
    
    void onZoom(GeoPoint oldTopLeft,
                GeoPoint oldCenter,
                GeoPoint oldBottomRight,
                GeoPoint newTopLeft,
                GeoPoint newCenter,
                GeoPoint newBottomRight,
                int oldZoomLevel,
                int newZoomLevel);
    
    void onClick(GeoPoint clickedPoint);
}
